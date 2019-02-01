package com.hwang.taskmaster;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ProjectTasks {

  @Embedded
  public Project project;

  @Relation(parentColumn = "projectId", entityColumn = "taskId", entity = Task.class)
  public List<Task> tasks;
}
