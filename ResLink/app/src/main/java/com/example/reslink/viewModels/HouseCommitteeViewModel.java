package com.example.reslink.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.reslink.data_models.Announcement;
import com.example.reslink.data_models.HouseCommitte;
import com.example.reslink.repositories.HouseCommitteeRepo;

import java.util.List;

public class HouseCommitteeViewModel extends ViewModel
{
    private HouseCommitteeRepo hcRepo;
    private MutableLiveData<List<HouseCommitte>> mHouseCommitteeList;
    private MutableLiveData<HouseCommitte> mHouseCommittee;
    private LiveData<String> mErrorMessage;
    private LiveData<Boolean> isLoading;

    public void init()
    {
        if( hcRepo != null)
        {
            return;
        }

        hcRepo = HouseCommitteeRepo.getInstance();
        isLoading = hcRepo.getIsLoading();
        mErrorMessage = hcRepo.getErrorMessage();
        mHouseCommittee = new MutableLiveData<>();
    }

    public LiveData<List<HouseCommitte>> getHCListByResId(String residenceId)
    {
        if(mHouseCommitteeList == null)
        {
            mHouseCommitteeList = hcRepo.getHCListByResId(residenceId);
        }

        return mHouseCommitteeList;
    }

    public void setHC(HouseCommitte houseCommittee)
    {
        mHouseCommittee.setValue(houseCommittee);
    }

    public LiveData<HouseCommitte> getHC()
    {
        return mHouseCommittee;
    }

    public LiveData<String> getErrorMessage()
    {
        return mErrorMessage;
    }

    public LiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

}
