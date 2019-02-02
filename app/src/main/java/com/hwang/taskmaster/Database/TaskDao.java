package com.hwang.taskmaster.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {

  @Query("SELECT * FROM task")
  List<Task> getAll();

  @Query("SELECT * FROM task WHERE taskId=:id")
  Task getById(long id);

  @Insert
  void add(Task task);

  @Delete
  void delete(Task task);

  @Update
  void update(Task task);
}
