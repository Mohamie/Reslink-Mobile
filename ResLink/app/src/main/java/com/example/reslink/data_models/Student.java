
package com.example.reslink.data_models;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class Student
{
  private String lastName;
  private String ownerId;
  private Date updated;
  private Date created;
  private String fullNames;
  private String objectId;
  private Integer studentNumber;
  private BackendlessUser studentAccount;
  private Gender gender;
  private int roomNumber;
  private String phoneNumber;

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName( String lastName )
  {
    this.lastName = lastName;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getFullNames()
  {
    return fullNames;
  }

  public void setFullNames( String fullNames )
  {
    this.fullNames = fullNames;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Integer getStudentNumber()
  {
    return studentNumber;
  }

  public void setStudentNumber( Integer studentNumber )
  {
    this.studentNumber = studentNumber;
  }

  public BackendlessUser getStudentAccount()
  {
    return studentAccount;
  }

  public void setStudentAccount( BackendlessUser studentAccount )
  {
    this.studentAccount = studentAccount;
  }

  public Gender getGender()
  {
    return gender;
  }

  public void setGender( Gender gender )
  {
    this.gender = gender;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}