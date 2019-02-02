package com.hwang.taskmaster.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface ProjectTaskDao {
  @Query("SELECT * FROM project WHERE id = :projectId")
  public List<ProjectTasks> loadProjectTask(long projectId);
}
