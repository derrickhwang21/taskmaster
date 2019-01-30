package com.hwang.taskmaster;

import android.text.TextUtils;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;


@Entity
public class Task implements Serializable {

  @PrimaryKey(autoGenerate = true)
  public int id;
  public String title;
  public String description;
  public String finishBy;
  @TypeConverters(StateConverter.class)
  public State taskState;

  public enum State {
    AVAILABLE("AVAILABLE"), ASSIGNED("ASSIGNED"), ACCEPTED("ACCEPTED"), FINISHED("FINISHED");
    protected final String value;

    State(String value) {
      this.value = value;
    }
  }

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

  public Task(){}

  public String toString(){
    return "Task name: " + title + " Description: " + description +  " Finish by: " + finishBy;
  }







  public void setTaskState(State state){
      this.taskState = state;
    }

    public State getState(){
      return taskState;
    }






}
