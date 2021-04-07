
package com.example.reslink.data_models;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class ResidenceManager
{
  private String managerFirstName;
  private String objectId;
  private String managerLastName;
  private Date updated;
  private Date created;
  private String ownerId;
  private BackendlessUser managerAccount;
  public String getManagerFirstName()
  {
    return managerFirstName;
  }

  public void setManagerFirstName( String managerFirstName )
  {
    this.managerFirstName = managerFirstName;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getManagerLastName()
  {
    return managerLastName;
  }

  public void setManagerLastName( String managerLastName )
  {
    this.managerLastName = managerLastName;
  }

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

  public BackendlessUser getManagerAccount()
  {
    return managerAccount;
  }

  public void setManagerAccount( BackendlessUser managerAccount )
  {
    this.managerAccount = managerAccount;
  }

}