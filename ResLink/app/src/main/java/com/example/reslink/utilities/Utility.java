package com.example.reslink.utilities;

import com.example.reslink.data_models.Residence;
import com.example.reslink.data_models.Student;

public class Utility
{
    private static Student loggedStudent;
    private static Residence loggedResidence;


    public static Student getLoggedStudent()
    {
        return loggedStudent;
    }

    public static void setLoggedStudent(Student loggedStudent)
    {
        Utility.loggedStudent = loggedStudent;
    }

    public static Residence getLoggedResidence()
    {
        return loggedResidence;
    }

    public static void setLoggedResidence(Residence loggedResidence)
    {
        Utility.loggedResidence = loggedResidence;
    }

}
