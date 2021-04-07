package com.example.reslink.repositories;

import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

import androidx.lifecycle.MutableLiveData;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.rt.data.EventHandler;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.data_models.Event;
import com.example.reslink.utilities.ApplicationClass;

import java.util.ArrayList;
import java.util.List;

public class EventRepo
{
    private static EventRepo instance;
    private MutableLiveData<List<Event>> mutableLiveData;
    private MutableLiveData<Event> eventUpdates;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> mErrorMessage;

    List<Event> events;

    //Singleton pattern
    public static EventRepo getInstance()
    {
        if(instance == null)
        {
            instance = new EventRepo();
        }

        return instance;
    }

    public MutableLiveData<List<Event>> getEvents(String residenceID)
    {
        if(mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();

        }
        setEvents(residenceID);
        mutableLiveData.setValue(events);
        return mutableLiveData;
    }

    public MutableLiveData<Event> getEventUpdates()
    {
        if(eventUpdates == null)
        {
            eventUpdates = new MutableLiveData<>();
        }

        eventUpdatesListener();
        return eventUpdates;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        if( isLoading == null)
        {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<String> getErrorMessage() {
        if (mErrorMessage == null) {
            mErrorMessage = new MutableLiveData<>();
        }

        return mErrorMessage;
    }

    private void setEvents(String residenceID)
    {
        events = new ArrayList<>();

        isLoading.setValue(true);
        String whereClause = "objectId in (Event[hc.student.studentAccount.residence.objectId = '" + residenceID +"'].objectId)";

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setSortBy("created DESC");

        queryBuilder.addRelated("hc.student");
        queryBuilder.addRelated("hc.hcRole");


        Backendless.Persistence.of(Event.class).find(queryBuilder,new AsyncCallback<List<Event>>() {
            @Override
            public void handleResponse(List<Event> response) {
                events = response;
                mutableLiveData.setValue(response);

                isLoading.setValue(false);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                mErrorMessage.setValue(fault.getMessage());
            }
        });
    }

    //RT Event Listener
    private void eventUpdatesListener()
    {
        eventUpdates.setValue(null);

        EventHandler<Event> eventEventHandler = Backendless.Data.of(Event.class).rt();

        String whereClause = "residenceID = '" + ApplicationClass.getLoggedResidence().getObjectId() + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        eventEventHandler.addCreateListener(whereClause, new AsyncCallback<Event>()
        {
            @Override
            public void handleResponse(Event response)
            {
                //Filter Event by residence, verify that this residence belongs to Currently logged User
                Backendless.Data.of(Event.class).findById(response.getObjectId(), queryBuilder, new AsyncCallback<Event>()
                {
                    @Override
                    public void handleResponse(Event response)
                    {
                        List<String> relations = new ArrayList<String>();
                        relations.add( "hc" );
                        relations.add( "hc.student" );
                        relations.add( "hc.hcRole" );

                        //get all related columns of Event Table
                        Backendless.Data.of(Event.class).findById(response.getObjectId(), relations, new AsyncCallback<Event>()
                        {
                            @Override
                            public void handleResponse(Event response)
                            {
                                eventUpdates.setValue(response);

                                //reset
                                eventUpdates.setValue(null);
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
