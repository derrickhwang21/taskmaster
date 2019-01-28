package com.hwang.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskMaster {

  @PrimaryKey(autoGenerate = true)
  public int id;
  public String title;
  public String description;
  public String timeStamp;
  public int status;

  public TaskMaster (String title, String description, String timeStamp, int status){
    this.title = title;
    this.description = description;
    this.timeStamp = timeStamp;
    this.status = status;
  }

  public String toString(){
    return "Task name: " + title + " Description: " + description + "Time: " + timeStamp + " Status: " + status;
  }

}
