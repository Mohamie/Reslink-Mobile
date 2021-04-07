package com.example.reslink.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reslink.data_models.Announcement;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.data_models.Event;
import com.example.reslink.repositories.EventRepo;

import java.util.List;

public class EventViewModel extends ViewModel {

    private EventRepo eventRepo;
    private MutableLiveData<List<Event>> mEvents;
    private MutableLiveData<Event> mEvent;
    private LiveData<Event> mEventUpdates;
    private LiveData<String> mErrorMessage;
    private MutableLiveData<Boolean> isLoading;
    List<Event> events;

    private String residenceID;

    public void init()
    {
        if(mEvents!=null)
        {
            return;
        }

        eventRepo = EventRepo.getInstance();
        mErrorMessage = eventRepo.getErrorMessage();
        mEventUpdates = eventRepo.getEventUpdates();

        mEvent = new MutableLiveData<>();
        isLoading = eventRepo.getIsLoading();
    }

    public LiveData<List<Event>> getEvents(String residenceID) {
        if (mEvents == null) {
            mEvents = eventRepo.getEvents(residenceID);
            this.residenceID = residenceID;
        }

        return mEvents;
    }

    public LiveData<Event> getEventUpdates()
    {
        return mEventUpdates;
    }

    public LiveData<String> getErrorMessage()
    {
        return mErrorMessage;
    }

    public void setEvent(Event event)
    {
        mEvent.setValue(event);
    }

    public LiveData<Event> getEvent()
    {
        return mEvent;
    }

    public LiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public void refreshList()
    {
        mEvents = eventRepo.getEvents(this.residenceID);
    }
}
