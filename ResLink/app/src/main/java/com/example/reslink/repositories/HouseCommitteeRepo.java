package com.example.reslink.repositories;

import androidx.lifecycle.MutableLiveData;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.reslink.data_models.HouseCommitte;

import java.util.ArrayList;
import java.util.List;

public class HouseCommitteeRepo
{
    private static  HouseCommitteeRepo instance;
    private MutableLiveData<List<HouseCommitte>> mHCList;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> mErrorMessage;

    List<HouseCommitte> houseCommitteeList;

    //Singleton
    public static HouseCommitteeRepo getInstance()
    {
        if(instance == null)
        {
            instance = new HouseCommitteeRepo();
        }

        return instance;
    }

    public MutableLiveData<List<HouseCommitte>> getHCListByResId(String residenceId)
    {
        if(mHCList == null)
        {
            mHCList = new MutableLiveData<>();
            isLoading = new MutableLiveData<>();
        }

        setHCList(residenceId);
        mHCList.setValue(houseCommitteeList);

        return mHCList;
    }

    public MutableLiveData<String> getErrorMessage()
    {
        if (mErrorMessage == null)
        {
            mErrorMessage = new MutableLiveData<>();
        }

        return mErrorMessage;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        if(isLoading == null)
        {
            isLoading = new MutableLiveData<>();
        }

        return isLoading;
    }

    private void setHCList(String residenceId)
    {
        houseCommitteeList = new ArrayList<>();

        isLoading.setValue(true);

        String whereClause = "objectId in (HouseCommittee[student.studentAccount.residence.objectId = '" + residenceId + "'].objectId)";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.addRelated("student");
        queryBuilder.addRelated("student.studentAccount");
        queryBuilder.addRelated("student.studentAccount.residence");
        queryBuilder.addRelated("hcRole");


        //Make an API Call here, relationship is HC > Student > Users > Residence
        Backendless.Data.of(HouseCommitte.class).find(queryBuilder, new AsyncCallback<List<HouseCommitte>>()
        {
            @Override
            public void handleResponse(List<HouseCommitte> response)
            {
                houseCommitteeList = response;
                mHCList.setValue(houseCommitteeList);
            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                mErrorMessage.setValue(fault.getMessage());
            }
        });
    }
}
