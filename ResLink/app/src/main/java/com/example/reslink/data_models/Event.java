
package com.example.reslink.data_models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Event
{
  private String ownerId;
  private Date updated;
  private Date eventStartTime;
  private Date eventEndTime;
  private String objectId;
  private Date created;
  private String eventDescription;
  private String eventTitle;
  private HouseCommitte hc;

  public boolean isExpanded() {
    return expanded;
  }

  public void setExpanded(boolean expanded) {
    this.expanded = false;
  }

  private boolean expanded;


  public Date getEventEventTime() {
    return eventEventTime;
  }

  public void setEventEventTime(Date eventEventTime) {
    this.eventEventTime = eventEventTime;
  }

  private Date eventEventTime;

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

  public String getEventDescription()
  {
    return eventDescription;
  }

  public void setEventDescription( String eventDescription )
  {
    this.eventDescription = eventDescription;
  }

  public Date getEventStartTime() {
    return eventStartTime;
  }

  public void setEventStartTime(Date eventStartTime) {
    this.eventStartTime = eventStartTime;
  }

  public Date getEventEndTime() {
    return eventEndTime;
  }

  public void setEventEndTime(Date eventEndTime) {
    this.eventEndTime = eventEndTime;
  }

  public String getEventTitle()
  {
    return eventTitle;
  }

  public void setEventTitle( String eventTitle )
  {
    this.eventTitle = eventTitle;
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
    Event event = (Event) o;
    return Objects.equals(ownerId, event.ownerId) &&
            Objects.equals(eventStartTime, event.eventStartTime) &&
            Objects.equals(eventEndTime, event.eventEndTime) &&
            Objects.equals(updated, event.updated) &&
            Objects.equals(objectId, event.objectId) &&
            Objects.equals(created, event.created) &&
            Objects.equals(eventDescription, event.eventDescription) &&
            Objects.equals(eventTitle, event.eventTitle) &&
            Objects.equals(hc, event.hc);
  }

  //ItemCallBack for ListAdapter
  public static DiffUtil.ItemCallback<Event> itemCallback = new DiffUtil.ItemCallback<Event>() {
    @Override
    public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
      return oldItem.getObjectId().equals(newItem.getObjectId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
      return oldItem.equals(newItem);
    }
  };
}