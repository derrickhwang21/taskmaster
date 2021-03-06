package com.hwang.taskmaster.Database;


import com.hwang.taskmaster.Database.StateConverter;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


@Entity(tableName = "task")
public class Task implements Serializable {

  @PrimaryKey(autoGenerate = true)
  public long taskId;
  public String title;
  public String description;
  public String finishBy;
  @TypeConverters(StateConverter.class)
  public State taskState;
  public String projectReference;

  public Task(){}

  public enum State {AVAILABLE("AVAILABLE"), ASSIGNED("ASSIGNED"), ACCEPTED("ACCEPTED"), FINISHED("FINISHED");
    public final String value;
    State(String value) {
      this.value = value;}}

  public String getTitle() { return title; }

  public void setTitle(String title) { this.title = title; }

  public String getDescription() { return description; }

  public void setDescription(String description) { this.description = description; }

  public String getFinishBy() { return finishBy; }

  public void setFinishBy(String finishBy) { this.finishBy = finishBy; }

  public State getState(){ return taskState;}

  public void setTaskState(State state){ this.taskState = state;}

  public String getProjecTitle() { return projectReference; }

  public void setProjecTitle(String projectReference) { this.projectReference = projectReference; }

  public String toString(){ return "Task name: " + title + " Description: " + description +  " Finish by: " + finishBy;}

}
