package com.hwang.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

  @PrimaryKey(autoGenerate = true)
  public int id;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getFinishBy() {
    return finishBy;
  }

  public void setFinishBy(String finishBy) {
    this.finishBy = finishBy;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String title;
  public String description;
  public String timeStamp;
  public String finishBy;
  public int status;

  public Task (String title, String description, String timeStamp, int status, String finishBy){
    this.title = title;
    this.description = description;
    this.timeStamp = timeStamp;
    this.status = status;
    this.finishBy = finishBy;
  }

  public Task(){}

  public String toString(){
    return "Task name: " + title + " Description: " + description + "Time: " + timeStamp + " Status: " + status + " Finish by: " + finishBy;
  }

}
