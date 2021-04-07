
package com.example.reslink.data_models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Complaint
{
  private String ownerId;
  private Date created;
  private String complaintTitle;
  private String complaintDescription;
  private String objectId;
  private Integer complaintVote;
  private Date updated;
  private Student student;
  private ComplaintStatus complaintStatus;

  public ComplaintStatus getComplaintStatus() {
    return complaintStatus;
  }

  public void setComplaintStatus(ComplaintStatus complaintStatus) {
    this.complaintStatus = complaintStatus;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getComplaintTitle()
  {
    return complaintTitle;
  }

  public void setComplaintTitle( String complaintTitle )
  {
    this.complaintTitle = complaintTitle;
  }

  public String getComplaintDescription()
  {
    return complaintDescription;
  }

  public void setComplaintDescription( String complaintDescription )
  {
    this.complaintDescription = complaintDescription;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated()
  {
    return updated;
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
  public boolean equals(Object o)
  {
    if(this == o)
      return true;

    if(o == null || getClass() != o.getClass())
      return false;

    Complaint that = (Complaint) o;
    return Objects.equals(ownerId,that.ownerId)&&
            Objects.equals(created,that.created)&&
            Objects.equals(objectId,that.objectId)&&
            Objects.equals(complaintTitle,that.complaintTitle)&&
            Objects.equals(complaintDescription,that.complaintDescription)&&
            Objects.equals(complaintVote,that.complaintVote)&&
            Objects.equals(updated,that.updated)&&
            Objects.equals(student,that.student);
  }


public static DiffUtil.ItemCallback<Complaint> itemCallback = new DiffUtil.ItemCallback<Complaint>() {
  @Override
  public boolean areItemsTheSame(@NonNull Complaint oldItem, @NonNull Complaint newItem) {
    return oldItem.getObjectId().equals(newItem.getObjectId());
  }

  @Override
  public boolean areContentsTheSame(@NonNull Complaint oldItem, @NonNull Complaint newItem) {
    return oldItem.equals(newItem);
  }
};
}

