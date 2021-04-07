
package com.example.reslink.data_models;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class Gender
{
  private Integer genderID;
  private String ownerId;
  private Date updated;
  private String genderName;
  private String objectId;
  private Date created;
  public Integer getGenderID()
  {
    return genderID;
  }

  public void setGenderID( Integer genderID )
  {
    this.genderID = genderID;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getGenderName()
  {
    return genderName;
  }

  public void setGenderName( String genderName )
  {
    this.genderName = genderName;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Date getCreated()
  {
    return created;
  }

}