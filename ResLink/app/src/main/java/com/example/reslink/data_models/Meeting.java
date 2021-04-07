
package com.example.reslink.data_models;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class Meeting
{
  private Date updated;
  private String meetingTitle;
  private String meetingDescription;
  private Date created;
  private String objectId;
  private String ownerId;
  public Date getUpdated()
  {
    return updated;
  }

  public String getMeetingTitle()
  {
    return meetingTitle;
  }

  public void setMeetingTitle( String meetingTitle )
  {
    this.meetingTitle = meetingTitle;
  }

  public String getMeetingDescription()
  {
    return meetingDescription;
  }

  public void setMeetingDescription( String meetingDescription )
  {
    this.meetingDescription = meetingDescription;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }
}