
package com.example.reslink.data_models;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class Residence
{
  private Date updated;
  private Date created;
  private String ownerId;
  private String objectId;
  private String residenceName;
  private Gender residenceGender;
  private ResidenceManager residenceManager;
  public Date getUpdated()
  {
    return updated;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getResidenceName()
  {
    return residenceName;
  }

  public void setResidenceName( String residenceName )
  {
    this.residenceName = residenceName;
  }

  public Gender getResidenceGender()
  {
    return residenceGender;
  }

  public void setResidenceGender( Gender residenceGender )
  {
    this.residenceGender = residenceGender;
  }

  public ResidenceManager getResidenceManager()
  {
    return residenceManager;
  }

  public void setResidenceManager( ResidenceManager residenceManager )
  {
    this.residenceManager = residenceManager;
  }
}