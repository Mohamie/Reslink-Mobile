package com.example.reslink.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.reslink.data_models.Announcement;
import com.example.reslink.repositories.AnnouncementRepo;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementViewModel extends ViewModel
{
    private AnnouncementRepo announcementRepo;
    private MutableLiveData<List<Announcement>> mAnnouncements;
    private MutableLiveData<Announcement> mAnnouncement;
    private MutableLiveData<Announcement> mAnnouncementUpdates;
    private LiveData<String> mErrorMessage;
    private LiveData<Boolean> isLoading;

    private String residenceID;

    List<Announcement> announcements;

    public void init()
    {
        if(mAnnouncements != null)
        {
            return;
        }

        announcementRepo = AnnouncementRepo.getInstance();

        mErrorMessage = announcementRepo.getErrorMessage();
        isLoading = announcementRepo.getIsLoading();
        mAnnouncement = new MutableLiveData<>();
        mAnnouncementUpdates = announcementRepo.getAnnouncementUpdates();
    }

    public LiveData<List<Announcement>> getAnnouncements(String residenceID)
    {
        if(mAnnouncements == null)
        {
            mAnnouncements = announcementRepo.getAnnouncements(residenceID);
            this.residenceID = residenceID;
        }

        return mAnnouncements;
    }

    public LiveData<Announcement> getAnnouncement()
    {
        return mAnnouncement;
    }

    public LiveData<Announcement> getAnnouncementUpdates()
    {
        return mAnnouncementUpdates;
    }

    public LiveData<String> getErrorMessage()
    {
        return mErrorMessage;
    }

    public LiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public void setAnnouncement(Announcement announcement)
    {
        mAnnouncement.setValue(announcement);
    }

    public void refreshList()
    {
        mAnnouncements = announcementRepo.getAnnouncements(this.residenceID);
    }
}
