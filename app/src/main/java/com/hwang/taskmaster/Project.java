package com.hwang.taskmaster;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project")
public class Project implements Serializable {
  @PrimaryKey(autoGenerate=true)
  public long projectId;
  public long taskId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String title;
  public String description;

  // Default constructor
  public Project() {}
}
