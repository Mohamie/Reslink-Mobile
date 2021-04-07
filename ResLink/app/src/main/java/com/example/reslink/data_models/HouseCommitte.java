
package com.example.reslink.data_models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HouseCommitte
{
  private String ownerId;
  private Date updated;
  private String objectId;
  private Date created;
  private BackendlessUser hcAccount;
  private HCRole hcRole;
  private Student student;
  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Date getCreated()
  {
    return created;
  }

  public BackendlessUser getHcAccount()
  {
    return hcAccount;
  }

  public void setHcAccount( BackendlessUser hcAccount )
  {
    this.hcAccount = hcAccount;
  }

  public HCRole getHcRole()
  {
    return hcRole;
  }

  public void setHcRole( HCRole hcRole )
  {
    this.hcRole = hcRole;
  }

  public Student getStudent()
  {
    return student;
  }

  public void setStudent( Student student )
  {
    this.student = student;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HouseCommitte that = (HouseCommitte) o;
    return Objects.equals(updated, that.updated) &&
            Objects.equals(created, that.created) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(objectId, that.objectId) &&
            Objects.equals(hcAccount, that.hcAccount) &&
            Objects.equals(hcRole, that.hcRole) &&
            Objects.equals(student, that.student);
  }


  public static DiffUtil.ItemCallback<HouseCommitte> itemCallback = new DiffUtil.ItemCallback<HouseCommitte>() {
    @Override
    public boolean areItemsTheSame(@NonNull HouseCommitte oldItem, @NonNull HouseCommitte newItem) {
      return oldItem.getObjectId().equals(newItem.getObjectId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull HouseCommitte oldItem, @NonNull HouseCommitte newItem) {
      return oldItem.equals(newItem);
    }
  };


  }