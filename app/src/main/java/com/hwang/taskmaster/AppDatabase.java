package com.hwang.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Task.class}, version = 8, exportSchema = false)
@TypeConverters({StateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
  public abstract TaskDao taskDao();
}
