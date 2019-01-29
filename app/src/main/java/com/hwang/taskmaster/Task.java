package com.hwang.taskmaster;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task implements Serializable {

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


  public String getFinishBy() {
    return finishBy;
  }

  public void setFinishBy(String finishBy) {
    this.finishBy = finishBy;
  }



  public String title;
  public String description;
  public String finishBy;


//  public Task (String title, String description, String finishBy){
//    this.title = title;
//    this.description = description;
//    this.finishBy = finishBy;
//  }

  public Task(){}

  public String toString(){
    return "Task name: " + title + " Description: " + description +  " Finish by: " + finishBy;
  }

}
