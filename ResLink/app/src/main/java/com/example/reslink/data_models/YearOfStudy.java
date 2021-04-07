
package com.example.reslink.data_models;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class YearOfStudy
{
  private Integer yearID;
  private String ownerId;
  private String yearName;
  private Date updated;
  private Date created;
  private String objectId;
  public Integer getYearID()
  {
    return yearID;
  }

  public void setYearID( Integer yearID )
  {
    this.yearID = yearID;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getYearName()
  {
    return yearName;
  }

  public void setYearName( String yearName )
  {
    this.yearName = yearName;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getObjectId()
  {
    return objectId;
  }

}