
package com.example.reslink.data_models;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import com.example.reslink.data_models.Meeting;

import java.util.Date;
import java.util.List;

public class Attendance
{
  private Date updated;
  private String ownerId;
  private Date created;
  private String objectId;
  private List<Student> student;
  private Meeting meeting;
  public Date getUpdated()
  {
    return updated;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public List<Student> getStudent()
  {
    return student;
  }

  public void setStudent( List<Student> student )
  {
    this.student = student;
  }

  public Meeting getMeeting()
  {
    return meeting;
  }

  public void setMeeting( Meeting meeting )
  {
    this.meeting = meeting;
  }

}