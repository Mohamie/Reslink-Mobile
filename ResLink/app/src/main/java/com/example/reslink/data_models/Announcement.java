
package com.example.reslink.data_models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;
import com.example.reslink.data_models.HouseCommitte;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Announcement
{
  private Date updated;
  private Date created;
  private String ownerId;
  private String objectId;
  private String announcementDescription;
  private String announcementTitle;
  private  HouseCommitte hc;
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

  public String getAnnouncementDescription()
  {
    return announcementDescription;
  }

  public void setAnnouncementDescription( String announcementDescription )
  {
    this.announcementDescription = announcementDescription;
  }

  public String getAnnouncementTitle()
  {
    return announcementTitle;
  }

  public void setAnnouncementTitle( String announcementTitle )
  {
    this.announcementTitle = announcementTitle;
  }

  public HouseCommitte getHc()
  {
    return hc;
  }

  public void setHc( HouseCommitte hc )
  {
    this.hc = hc;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Announcement that = (Announcement) o;
    return Objects.equals(updated, that.updated) &&
            Objects.equals(created, that.created) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(objectId, that.objectId) &&
            Objects.equals(announcementDescription, that.announcementDescription) &&
            Objects.equals(announcementTitle, that.announcementTitle) &&
            Objects.equals(hc, that.hc);
  }



  //ItemCallBack for ListAdapter
  public static DiffUtil.ItemCallback<Announcement> itemCallback = new DiffUtil.ItemCallback<Announcement>() {
    @Override
    public boolean areItemsTheSame(@NonNull Announcement oldItem, @NonNull Announcement newItem) {
      return oldItem.getObjectId().equals(newItem.getObjectId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Announcement oldItem, @NonNull Announcement newItem) {
      return oldItem.equals(newItem);
    }
  };

}