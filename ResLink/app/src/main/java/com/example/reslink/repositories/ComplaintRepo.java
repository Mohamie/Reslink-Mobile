package com.example.reslink.repositories;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.rt.data.EventHandler;
import com.example.reslink.data_models.Announcement;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.data_models.ComplaintStatus;
import com.example.reslink.data_models.Residence;
import com.example.reslink.data_models.Student;
import com.example.reslink.utilities.ApplicationClass;
import com.example.reslink.views.MainActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintRepo
{


    private static final String TAG = "ComplaintError";
    private static ComplaintRepo instance;
    private MutableLiveData<List<Complaint>> mutableLiveData;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> mErrorMessage;
    private MutableLiveData<Complaint> savedComplaint;
    private MutableLiveData<Complaint> complaintUpdates;


    List<Complaint> complaints;

    //Singleton pattern
    public static ComplaintRepo getInstance()
    {
        if(instance == null)
        {
            instance = new ComplaintRepo();
        }

        return instance;
    }

    public MutableLiveData<List<Complaint>> getComplaints(String residenceID)
    {
        if(mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();

        }
        setComplaints(residenceID);
        mutableLiveData.setValue(complaints);

        return mutableLiveData;
    }

    public MutableLiveData<Complaint> getComplaintUpdates()
    {
        if(complaintUpdates == null)
        {
            complaintUpdates = new MutableLiveData<>();
        }

        ComplaintUpdatesListener();

        return complaintUpdates;
    }


    public void setDefaultStatus()
    {

        String whereClause = "statusID = 0";

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        Backendless.Data.of(ComplaintStatus.class).find(queryBuilder, new AsyncCallback<List<ComplaintStatus>>()
        {
            @Override
            public void handleResponse(List<ComplaintStatus> response)
            {
                ApplicationClass.setDefaultStatus(response.get(0));
            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                mErrorMessage.setValue(fault.getMessage());
            }
        });
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
    public MutableLiveData<Complaint> getSavedComplaint()
    {
        if( savedComplaint == null)
        {
            savedComplaint = new MutableLiveData<>();
        }
        return savedComplaint;
    }

    public void addComplaint(Complaint complaint)
    {

        HashMap newComplaint = new HashMap();
        newComplaint.put("complaintTitle", complaint.getComplaintTitle());
        newComplaint.put("complaintDescription", complaint.getComplaintDescription());

        isLoading.setValue(true);
        Backendless.Data.of("Complaint").save(newComplaint, new AsyncCallback<Map>()
        {
            @Override
            public void handleResponse(Map response)
            {
                List<String> relations = new ArrayList<>();
                relations.add("student");
                relations.add("resolver");

                String complaintId = response.get("objectId").toString();
                Backendless.Data.of(Complaint.class).findById(complaintId, relations, new AsyncCallback<Complaint>()
                {
                    @Override
                    public void handleResponse(Complaint response)
                    {
                        //add complaint to memory
                       // complaints.add(response);

                        mutableLiveData.setValue(complaints);

                        ComplaintRepo.this.savedComplaint.setValue(response);
                        isLoading.setValue(false);
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
                isLoading.setValue(false);
            }
        });
    }


    public void setRelation(Complaint parentObject, String relationColumnName, List<Object> children)
    {
      Backendless.Data.of(Complaint.class).setRelation(parentObject, relationColumnName, children, new AsyncCallback<Integer>() {
          @Override
          public void handleResponse(Integer response)
          {
              complaints.set(response,parentObject);
          }

          @Override
          public void handleFault(BackendlessFault fault)
          {
             mErrorMessage.setValue(fault.getMessage());
          }
      });
    }

    private void setComplaints(String residenceID)
    {
        complaints = new ArrayList<>();
        isLoading.setValue(true);

        String whereClause = "objectId in (Complaint[student.studentAccount.residence.objectId = '" + residenceID +"'].objectId)";


        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setSortBy("created DESC");
        queryBuilder.addRelated("student");
        queryBuilder.addRelated("complaintStatus");


        Backendless.Persistence.of(Complaint.class).find(queryBuilder,new AsyncCallback<List<Complaint>>() {
            @Override
            public void handleResponse(List<Complaint> response) {
                complaints = response;
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

    //RT Complaint Event Listener
    private void ComplaintUpdatesListener()
    {
        complaintUpdates.setValue(null);

        EventHandler<Complaint> complaintEventHandler = Backendless.Data.of(Complaint.class).rt();

        String whereClause = "residenceID = '" + ApplicationClass.getLoggedResidence().getObjectId() + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        complaintEventHandler.addCreateListener(whereClause, new AsyncCallback<Complaint>()
        {
            @Override
            public void handleResponse(Complaint response)
            {
                //Filter Complaint by residence, verify that this residence belongs to Currently logged User
                Backendless.Data.of(Complaint.class).findById(response.getObjectId(), queryBuilder, new AsyncCallback<Complaint>()
                {
                    @Override
                    public void handleResponse(Complaint response)
                    {
                        List<String> relations = new ArrayList<String>();
                        relations.add( "student" );
                        relations.add( "complaintStatus" );

                        //get all related columns of Event Table
                        Backendless.Data.of(Complaint.class).findById(response.getObjectId(), relations, new AsyncCallback<Complaint>()
                        {
                            @Override
                            public void handleResponse(Complaint response)
                            {
                                complaintUpdates.setValue(response);

                                //reset
                                complaintUpdates.setValue(null);
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
