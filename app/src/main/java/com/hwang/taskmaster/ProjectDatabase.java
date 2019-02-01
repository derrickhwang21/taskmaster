package com.hwang.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Project.class})
public abstract class ProjectDatabase extends RoomDatabase {
  public abstract ProjectDao projectDao();
}
