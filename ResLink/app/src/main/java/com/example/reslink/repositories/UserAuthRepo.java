package com.example.reslink.repositories;

import androidx.lifecycle.MutableLiveData;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.example.reslink.data_models.Residence;
import com.example.reslink.data_models.Student;
import com.example.reslink.utilities.ApplicationClass;

import java.util.List;

public class UserAuthRepo
{
    private MutableLiveData<BackendlessUser> mUserAccount;
    private MutableLiveData<String> mErrorMessage;
    private MutableLiveData<Boolean> mIsAuthenticated;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsLogged;
    private MutableLiveData<Student> mStudent;

    private static UserAuthRepo instance;

    public static UserAuthRepo getInstance()
    {
        if(instance == null)
        {
            instance = new UserAuthRepo();
        }
        return instance;
    }

    public MutableLiveData<BackendlessUser> getUserAccount()
    {
        if(mUserAccount == null)
        {
            mUserAccount = new MutableLiveData<>();
        }

        return mUserAccount;
    }

    public MutableLiveData<String> getErrorMessage()
    {
        if(mErrorMessage == null)
        {
            mErrorMessage = new MutableLiveData<>();
        }

        return mErrorMessage;
    }

    public MutableLiveData<Boolean> isAuthenticated()
    {
        if(mIsAuthenticated == null)
        {
            mIsAuthenticated = new MutableLiveData<>();
        }

        return mIsAuthenticated;
    }

    public MutableLiveData<Boolean> isLoading()
    {
        if (mIsLoading == null)
        {
            mIsLoading = new MutableLiveData<>();
        }
        return mIsLoading;
    }

    public MutableLiveData<Boolean> isLogged() {
        if (mIsLogged == null) {
            mIsLogged = new MutableLiveData<>();
        }
        return mIsLogged;
    }


    public MutableLiveData<Student> getStudent()
    {
        if(mStudent == null)
        {
            mStudent = new MutableLiveData<>();
        }

        return mStudent;
    }

    //Meant for LoginActivity
    public void loginUser(String email, String password, boolean stayLogged)
    {

        mIsLoading.setValue(true);
        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>()
        {
            @Override
            public void handleResponse(BackendlessUser response)
            {
                mUserAccount.setValue(response);
                mIsAuthenticated.setValue(true);

                //set Student
                setLoggedStudent(response.getObjectId());

                //this will also find the Residence a student reside in
                findLoggedUser(response.getObjectId());
            }
            @Override
            public void handleFault(BackendlessFault fault)
            {
                mIsAuthenticated.setValue(false);
                mIsLoading.setValue(false);
                mErrorMessage.setValue(fault.getMessage());
            }
        }, stayLogged);
    }

    public void logoutUser()
    {
        mIsLoading.setValue(true);
        Backendless.UserService.logout(new AsyncCallback<Void>()
        {
            @Override
            public void handleResponse(Void response)
            {
                //Do something when the user is logged out
                mIsLoading.setValue(false);
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                mErrorMessage.setValue(fault.getMessage());
            }
        });
    }

    public void resetPassword(String email)
    {
        mIsLoading.setValue(true);
        Backendless.UserService.restorePassword(email, new AsyncCallback<Void>()
        {
            @Override
            public void handleResponse(Void response)
            {
                //Do something when the user is logged out
                mIsLoading.setValue(false);
            }
            @Override
            public void handleFault(BackendlessFault fault)
            {
                mIsLoading.setValue(false);
                mErrorMessage.setValue(fault.getMessage());
            }
        });
    }

    public void checkLoggedUser()
    {
        mIsLoading.setValue(true);
        mIsLogged.setValue(false);

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response)
            {
                if(response)
                {
                    String userObjectId = UserIdStorageFactory.instance().getStorage().get();
                    findLoggedUser(userObjectId);
                }
                else
                {
                    mIsLogged.setValue(false);
                    mIsLoading.setValue(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                mIsLoading.setValue(false);
                mIsLogged.setValue(false);
                mErrorMessage.setValue(fault.getMessage());
            }
        });
    }

    private void findLoggedUser(String userObjectId)
    {
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.addRelated("residence");
        queryBuilder.addRelated("residence.residenceManager");


        Backendless.Data.of(BackendlessUser.class).findById(userObjectId, queryBuilder, new AsyncCallback<BackendlessUser>()
        {
            @Override
            public void handleResponse(BackendlessUser response)
            {
                mUserAccount.setValue(response);
                setLoggedStudent(response.getObjectId());
                ApplicationClass.setLoggedResidence(((Residence)response.getProperty("residence")));
                mIsLoading.setValue(false);
                mIsLogged.setValue(true);
            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                mIsLoading.setValue(false);
                mIsLogged.setValue(false);
                mErrorMessage.setValue(fault.getMessage());
            }
        });

    }

    private void setLoggedStudent(String userObjectId)
    {
        if(mStudent == null)
        {
            mStudent = new MutableLiveData<>();
        }
        //whereClause to select a Student Object from db using User Account of that student
        String whereClause = "objectId in (Student[studentAccount.objectId = '" + userObjectId + "'].objectId)";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.addRelated("studentAccount");
        queryBuilder.addRelated("studentAccount.residence");

        Backendless.Data.of(Student.class).find(queryBuilder, new AsyncCallback<List<Student>>()
        {
            @Override
            public void handleResponse(List<Student> response)
            {
                //Will return only one object in list
                mStudent.setValue(response.get(0));
                //For Utility use
                ApplicationClass.setLoggedStudent(response.get(0));
            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                mIsLoading.setValue(false);
                mErrorMessage.setValue(fault.getMessage());
            }
        });
    }
}
