package com.example.reslink.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.reslink.data_models.Announcement;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.data_models.Residence;
import com.example.reslink.data_models.Student;
import com.example.reslink.repositories.ComplaintRepo;

import java.util.List;

public class ComplaintViewModel extends ViewModel {

    private ComplaintRepo complaintRepo;
    private MutableLiveData<List<Complaint>> mComplaints;
    private MutableLiveData<Complaint> mComplaint;
    private LiveData<Complaint> savedComplaint;
    private MutableLiveData<Complaint> mComplaintUpdates;
    private LiveData<String> mErrorMessage;
    private LiveData<Boolean> isLoading;

    private String residenceID;


    List<Complaint>complaints;

    public void init()
    {
        if(mComplaints!=null)
        {
            return;
        }

        complaintRepo = ComplaintRepo.getInstance();

        mErrorMessage = complaintRepo.getErrorMessage();
        isLoading = complaintRepo.getIsLoading();
        mComplaint = new MutableLiveData<>();
        savedComplaint = complaintRepo.getSavedComplaint();
        mComplaintUpdates = complaintRepo.getComplaintUpdates();
    }
    public LiveData<List<Complaint>> getComplaints(String residenceID)
    {
        if(mComplaints == null)
        {
            mComplaints = complaintRepo.getComplaints(residenceID);
            this.residenceID = residenceID;
        }

        return mComplaints;
    }
    public LiveData<String> getErrorMessage()
    {
        return mErrorMessage;
    }

    public void setComplaint(Complaint complaint)
    {
        mComplaint.setValue(complaint);
    }

    public LiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public LiveData<Complaint> getComplaint()
    {
        return mComplaint;
    }

    public LiveData<Complaint> getComplaintUpdates()
    {
        return mComplaintUpdates;
    }

    public LiveData<Complaint>getSavedComplaint()
    {
        return savedComplaint;
    }

    public void addComplaint(Complaint newComplaint)
    {
        complaintRepo.addComplaint(newComplaint);
    }

    public void setDefaultStatus()
    {
        complaintRepo.setDefaultStatus();
    }

    public void setRelation(Complaint parentObject, String relationColumnName, List<Object> children)
    {
        complaintRepo.setRelation(parentObject, relationColumnName, children);
    }

    public void refreshList()
    {
        mComplaints = complaintRepo.getComplaints(this.residenceID);
    }
}
