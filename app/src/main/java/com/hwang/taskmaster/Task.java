package com.hwang.taskmaster;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.hwang.taskmaster.Task.State.ACCEPTED;
import static com.hwang.taskmaster.Task.State.ASSIGNED;
import static com.hwang.taskmaster.Task.State.AVAILABLE;
import static com.hwang.taskmaster.Task.State.FINISHED;

@Entity
public class Task implements Serializable {

  @PrimaryKey(autoGenerate = true)
  public int id;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getFinishBy() {
    return finishBy;
  }

  public void setFinishBy(String finishBy) {
    this.finishBy = finishBy;
  }

//public boolean isFinished(){
//    return finished;
//}

public void setFinished(boolean finished){
    this.finished = finished;
}

  public String title;
  public String description;
  public String finishBy;
  public boolean finished;
  public Enum State;


//  public Task (String title, String description, String finishBy){
//    this.title = title;
//    this.description = description;
//    this.finishBy = finishBy;
//  }

  public Task(){}

  public String toString(){
    return "Task name: " + title + " Description: " + description +  " Finish by: " + finishBy;
  }

//  public class ConstantState{
//    public static final String AVAILABLE = "Available";
//    public static final String ASSIGNED = "Assigned";
//    public static final String ACCEPTED = "Accepted";
//    public static final String FINISHED = "Finished";
//  }



//  public enum State {
//    AVAILABLE("Available"), ASSIGNED("Assigned"), ACCEPTED("Accepted"), FINISHED("Finished");
//
//    private String value;
//
//    private State(String value){
//      this.value = value;
//    }
//  }

    public enum State {
    AVAILABLE("Available"), ASSIGNED("Assigned"), ACCEPTED("Accepted"), FINISHED("Finished");
    private String value;

    private State(String value) {
      this.value = value;
    }

//    public String getState(){
//      return ;
//    }

//    public String getState(){
//      switch(this){
//        case AVAILABLE:
//           return AVAILABLE.value;
//        case ASSIGNED:
//          return ASSIGNED.value;
//        case ACCEPTED:
//          return ACCEPTED.value;
//        case FINISHED:
//          return FINISHED.value;
//      }
//      return null;
//    }
  }



//  public String isAccepted(){
//    return State.ACCEPTED.value;
//  }
//
//  public String isAssigned(){
//    return State.ASSIGNED.value;
//  }
//
//  public String isAvailable(){
//    return State.AVAILABLE.value;
//  }
//
//  public String isFinished(){
//    return State.FINISHED.value;
//  }

}
