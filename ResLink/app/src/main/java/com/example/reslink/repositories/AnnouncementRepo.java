package com.example.reslink.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.rt.data.EventHandler;
import com.example.reslink.data_models.Announcement;
import com.example.reslink.utilities.ApplicationClass;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementRepo
{
   private static AnnouncementRepo instance;
   private MutableLiveData<List<Announcement>> mutableLiveData;
   private MutableLiveData<Boolean> isLoading;
   private MutableLiveData<String> mErrorMessage;
   private MutableLiveData<Announcement> announcementUpdates;

   String residenceId;


   List<Announcement> announcements;

   //Singleton pattern
   public static AnnouncementRepo getInstance()
   {
      if(instance == null)
      {
         instance = new AnnouncementRepo();
      }

      return instance;
   }

   public MutableLiveData<List<Announcement>> getAnnouncements(String residenceID)
   {
      if(mutableLiveData == null)
      {
         mutableLiveData = new MutableLiveData<>();

      }

      this.residenceId = residenceID;
      setAnnouncements(residenceID);
      mutableLiveData.setValue(announcements);

      return mutableLiveData;
   }

   public MutableLiveData<Announcement> getAnnouncementUpdates()
   {
      if(announcementUpdates == null)
      {
         announcementUpdates = new MutableLiveData<>();
      }

      AnnouncementUpdatesListener();

      return announcementUpdates;
   }

   public MutableLiveData<Boolean> getIsLoading()
   {
      if( isLoading == null)
      {
         isLoading = new MutableLiveData<>();
      }
      return isLoading;
   }

   public MutableLiveData<String> getErrorMessage()
   {
      if(mErrorMessage == null)
      {
         mErrorMessage = new MutableLiveData<>();
      }

      return mErrorMessage;
   }

   private void setAnnouncements(String residenceID)
   {
      announcements = new ArrayList<>();

      isLoading.setValue(true);

      String whereClause = "objectId in (Announcement[hc.student.studentAccount.residence.objectId = '" + residenceID +"'].objectId)";

      DataQueryBuilder queryBuilder = DataQueryBuilder.create();
      queryBuilder.setWhereClause(whereClause);
      queryBuilder.setSortBy("created DESC");
      queryBuilder.addRelated("hc.student");
      queryBuilder.addRelated("hc.hcRole");


      Backendless.Persistence.of(Announcement.class).find(queryBuilder, new AsyncCallback<List<Announcement>>()
      {
         @Override
         public void handleResponse(List<Announcement> response)
         {
            announcements = response;
            mutableLiveData.setValue(response);

            isLoading.setValue(false);
         }

         @Override
         public void handleFault(BackendlessFault fault)
         {
            mErrorMessage.setValue(fault.getMessage());
         }
      });
   }



   //Real Time Events Listeners
   private void AnnouncementUpdatesListener()
   {
      announcementUpdates.setValue(null);

      EventHandler<Announcement> announcementEventHandler = Backendless.Data.of(Announcement.class).rt();

      String whereClause = "residenceID = '" + ApplicationClass.getLoggedResidence().getObjectId() + "'";
      DataQueryBuilder queryBuilder = DataQueryBuilder.create();
      queryBuilder.setWhereClause(whereClause);

      announcementEventHandler.addCreateListener(whereClause, new AsyncCallback<Announcement>()
      {
         @Override
         public void handleResponse(Announcement response)
         {

            //Filter by residence, verify that this residence belongs to Currently logged
            Backendless.Data.of(Announcement.class).findById(response.getObjectId(), queryBuilder, new AsyncCallback<Announcement>()
            {
               @Override
               public void handleResponse(Announcement response)
               {
                  List<String> relations = new ArrayList<String>();
                  relations.add( "hc" );
                  relations.add( "hc.student" );
                  relations.add( "hc.hcRole" );
                  //get all related columns of Announcement Table
                  Backendless.Data.of(Announcement.class).findById(response.getObjectId(), relations, new AsyncCallback<Announcement>()
                  {
                     @Override
                     public void handleResponse(Announcement response)
                     {
                        announcementUpdates.setValue(response);

                        announcementUpdates.setValue(null);
                     }

                     @Override
                     public void handleFault(BackendlessFault fault)
                     {
                        mErrorMessage.setValue(fault.getMessage());
                     }
                  });
               }

               @Override
               public void handleFault(BackendlessFault fault)
               {
                  mErrorMessage.setValue(fault.getMessage());
               }
            });
         }

         @Override
         public void handleFault(BackendlessFault fault)
         {
            mErrorMessage.setValue(fault.getMessage());
         }
      });
   }






}
