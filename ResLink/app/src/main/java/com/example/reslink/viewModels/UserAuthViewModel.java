package com.example.reslink.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.backendless.BackendlessUser;
import com.example.reslink.data_models.Student;
import com.example.reslink.repositories.UserAuthRepo;

public class UserAuthViewModel extends ViewModel
{
    private UserAuthRepo userAuthRepo;
    private MutableLiveData<BackendlessUser> mUserAccount;
    private MutableLiveData<Boolean> mIsAuthenticated;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsLogged;
    private MutableLiveData<String> mErrorMessage;
    private MutableLiveData<Student> mStudent;

    public void Init()
    {
        if(mUserAccount != null)
        {
            return;
        }

        userAuthRepo = UserAuthRepo.getInstance();
        mUserAccount = userAuthRepo.getUserAccount();
        mIsAuthenticated = userAuthRepo.isAuthenticated();
        mIsLoading = userAuthRepo.isLoading();
        mIsLogged = userAuthRepo.isLogged();
        mErrorMessage = userAuthRepo.getErrorMessage();
        mStudent = userAuthRepo.getStudent();

    }

    public LiveData<BackendlessUser> getUserAccount()
    {
        return mUserAccount;
    }

    public LiveData<String> getErrorMessage()
    {
        return mErrorMessage;
    }

    public LiveData<Boolean> isAuthenticated()
    {
        return mIsAuthenticated;
    }

    public LiveData<Boolean> isLoading()
    {
        return mIsLoading;
    }

    //verifies if user is Logged
    public LiveData<Boolean> isLogged()
    {
        return mIsLogged;
    }

    public LiveData<Student> getStudent()
    {
        return mStudent;
    }

    public void loginUser(String email, String password, Boolean stayLogged)
    {
        userAuthRepo.loginUser(email, password, stayLogged);
    }

    public void logoutUser()
    {
        userAuthRepo.logoutUser();
    }

    public void resetPassword(String email)
    {
        userAuthRepo.resetPassword(email);
    }

    public void checkLoggedUser()
    {
        userAuthRepo.checkLoggedUser();
    }


}
