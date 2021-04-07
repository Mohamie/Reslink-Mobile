package com.example.reslink.utilities;

import android.app.Application;

import androidx.navigation.ui.AppBarConfiguration;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.example.reslink.data_models.Announcement;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.data_models.ComplaintStatus;
import com.example.reslink.data_models.Event;
import com.example.reslink.data_models.Gender;
import com.example.reslink.data_models.HCRole;
import com.example.reslink.data_models.HouseCommitte;
import com.example.reslink.data_models.Residence;
import com.example.reslink.data_models.Student;

public class ApplicationClass extends Application
{
    public static final String APPLICATION_ID = "A77E777B-228F-C5D4-FF44-76E32569F500";
    public static final String API_KEY = "C261C506-C746-4F8F-85EC-F9250E9C6BA1";
    public static final String SERVER_URL = "https://api.backendless.com";

    private static Student loggedStudent;
    private static Residence loggedResidence;
    private static ComplaintStatus defaultStatus;
    private static BackendlessUser user;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );

        Backendless.Data.mapTableToClass( "Announcement", Announcement.class );
        Backendless.Data.mapTableToClass( "Complaint", Complaint.class );
        Backendless.Data.mapTableToClass( "Event", Event.class );
        Backendless.Data.mapTableToClass( "Student", Student.class );
        Backendless.Data.mapTableToClass( "Gender", Gender.class );
        Backendless.Data.mapTableToClass( "HouseCommittee", HouseCommitte.class );
        Backendless.Data.mapTableToClass( "HCRole", HCRole.class );
        Backendless.Data.mapTableToClass( "HCRole", HCRole.class );
        Backendless.Data.mapTableToClass( "Residence", Residence.class );
        Backendless.Data.mapTableToClass( "ComplaintStatus", ComplaintStatus.class );

    }

    public static Student getLoggedStudent()
    {
        return loggedStudent;
    }

    public static void setLoggedStudent(Student loggedStudent)
    {
        ApplicationClass.loggedStudent = loggedStudent;
    }

    public static Residence getLoggedResidence()
    {
        return loggedResidence;
    }

    public static void setLoggedResidence(Residence loggedResidence)
    {
        ApplicationClass.loggedResidence = loggedResidence;
    }

    public static BackendlessUser getUser() {
        return user;
    }

    public static void setUser(BackendlessUser user) {
        ApplicationClass.user = user;
    }

    public static ComplaintStatus getDefaultStatus()
    {
        return defaultStatus;
    }

    public static void setDefaultStatus(ComplaintStatus defaultStatus)
    {
        ApplicationClass.defaultStatus = defaultStatus;
    }



}
