
package com.example.reslink.data_models;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class HCRole
{
  private String hcRole;
  private Integer hcRoleID;
  private Date created;
  private String ownerId;
  private String objectId;
  private Date updated;
  public String getHcRole()
  {
    return hcRole;
  }

  public void setHcRole( String hcRole )
  {
    this.hcRole = hcRole;
  }

  public Integer getHcRoleID()
  {
    return hcRoleID;
  }

  public void setHcRoleID( Integer hcRoleID )
  {
    this.hcRoleID = hcRoleID;
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

  public Date getUpdated()
  {
    return updated;
  }

                                                    

}