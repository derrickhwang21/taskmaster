package com.hwang.taskmaster.Database;

import com.hwang.taskmaster.Database.Project;
import com.hwang.taskmaster.Database.ProjectDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Project.class}, version = 1, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {
  public abstract ProjectDao projectDao();
}
