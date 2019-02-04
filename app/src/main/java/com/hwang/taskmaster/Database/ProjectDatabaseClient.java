package com.hwang.taskmaster.Database;

import android.content.Context;

import androidx.room.Room;

public class ProjectDatabaseClient {

  private Context mCtx;
  private static ProjectDatabaseClient mInstance;

  private ProjectDatabase projectDatabase;

  private ProjectDatabaseClient(Context mCtx){
    this.mCtx = mCtx;

    projectDatabase = Room.databaseBuilder(mCtx, ProjectDatabase.class, "project").fallbackToDestructiveMigration().build();
  }

  public static synchronized ProjectDatabaseClient getInstance(Context mCtx){
    if(mInstance == null){
      mInstance = new ProjectDatabaseClient(mCtx);
    }
    return mInstance;
  }

  public ProjectDatabase getProjectDatabase(){
    return projectDatabase;
  }
}



