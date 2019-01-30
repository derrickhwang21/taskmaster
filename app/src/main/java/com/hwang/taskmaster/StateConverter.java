package com.hwang.taskmaster;

import android.text.TextUtils;

import androidx.room.TypeConverter;


public class StateConverter {

  @TypeConverter
  public static Task.State toState(String state) {
    if (TextUtils.isEmpty(state)) {
      return Task.State.AVAILABLE;
    }
    return Task.State.valueOf(state);
  }

  @TypeConverter
  public static String getStateString(Task.State state) {

    if (state != null)
      return state.value;

    return null;
  }
}
