package com.hwang.taskmaster;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TaskMasterDao {

  @Query("SELECT * FROM taskMaster")
  List<TaskMaster> getAll();

  @Query("SELECT * FROM taskMaster WHERE id=:id")
  TaskMaster getById(long id);

  @Insert
  void add(TaskMaster taskMaster);
}
