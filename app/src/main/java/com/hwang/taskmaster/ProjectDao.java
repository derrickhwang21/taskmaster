package com.hwang.taskmaster;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProjectDao {

  @Query("SELECT * FROM project")
  List<Project> getAll();

  @Query("SELECT * FROM project WHERE projectId=:id")
  Project getProjectById(long id);

  @Insert
  void insertProject(Project project);

  @Delete
  void delete(Project project);

  @Update
  void update(Project project);
  }

