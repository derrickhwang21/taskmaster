package com.hwang.taskmaster.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hwang.taskmaster.Database.DatabaseClient;
import com.hwang.taskmaster.Database.Task;
import com.hwang.taskmaster.R;

import java.util.HashMap;
import java.util.Map;

import static com.hwang.taskmaster.Database.Task.State.ACCEPTED;
import static com.hwang.taskmaster.Database.Task.State.ASSIGNED;
import static com.hwang.taskmaster.Database.Task.State.AVAILABLE;
import static com.hwang.taskmaster.Database.Task.State.FINISHED;

public class UpdateTaskActivity extends AppCompatActivity {

  private EditText  editTextDescription, editTextFinishBy;
  private TextView editTextTitle;
  private RadioGroup checkRadioState;
  private RadioButton available, assigned, accepted, finished;
  private Task.State currentState = AVAILABLE;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_task);


    editTextTitle = findViewById(R.id.editTextTask);
    editTextDescription = findViewById(R.id.editTextDesc);
    editTextFinishBy = findViewById(R.id.editTextFinishBy);
    checkRadioState = findViewById(R.id.radioStatus);
    available = findViewById(R.id.available);
    assigned = findViewById(R.id.assigned);
    accepted = findViewById(R.id.accepted);
    finished = findViewById(R.id.finished);

    final Task task = (Task) getIntent().getSerializableExtra("task");

    loadTask(task);

    // Updated Listener
    findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view){
        Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
        updateTask(task);
      }
    });

    // Delete Listener
    findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            deleteTask(task);
          }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
          }
        });
        AlertDialog ad = builder.create();
        ad.show();
      }
    });

// State Listener
    checkRadioState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.finished){
          currentState = FINISHED;
          task.setTaskState(FINISHED);
        }else if(checkedId == R.id.assigned){
          currentState = ASSIGNED;
          task.setTaskState(ASSIGNED);
        }else if(checkedId == R.id.accepted){
          currentState = ACCEPTED;
          task.setTaskState(ACCEPTED);
        }else{
          currentState = AVAILABLE;
          task.setTaskState(AVAILABLE);
        }
      }
    });
  }

  private void loadTask(Task task){

    editTextTitle.setText(task.getTitle());
    editTextDescription.setText(task.getDescription());
    editTextFinishBy.setText(task.getFinishBy());
    switch(task.getState()){
        case AVAILABLE:
          available.setChecked(true);
          assigned.setChecked(false);
          accepted.setChecked(false);
          finished.setChecked(false);
          break;
      case ASSIGNED:
          available.setChecked(false);
          assigned.setChecked(true);
          accepted.setChecked(false);
          finished.setChecked(false);
          break;
        case ACCEPTED:
          available.setChecked(false);
          assigned.setChecked(false);
          accepted.setChecked(true);
          finished.setChecked(false);
          break;
      case FINISHED:
          available.setChecked(false);
          assigned.setChecked(false);
          accepted.setChecked(false);
          finished.setChecked(true);
          break;
      }
    }



  private void updateTask(final Task task){
    final String sTask = editTextTitle.getText().toString().trim();
    final String sDesc = editTextDescription.getText().toString().trim();
    final String sFinishBy = editTextFinishBy.getText().toString().trim();

    if(sTask.isEmpty()){
      editTextTitle.setError("Task Required");
      editTextTitle.requestFocus();
      return;
    }
    if(sDesc.isEmpty()){
      editTextDescription.setError("Description Required");
      editTextDescription.requestFocus();
      return;
    }
    if(sFinishBy.isEmpty()){
      editTextFinishBy.setError("Finished By Required");
      editTextFinishBy.requestFocus();
      return;
    }

    class UpdateTask extends AsyncTask<Void, Void, Void>{
      @Override
      protected Void doInBackground(Void... voids){
        task.setTitle(sTask);
        task.setDescription(sDesc);
        task.setFinishBy(sFinishBy);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference taskReference = db.collection("project").document(task.getProjecTitle());
        Map<String, Object> fireTask = new HashMap<>();
        fireTask.put("name", sTask);
        fireTask.put("description", sDesc);
        fireTask.put("finishBy", sFinishBy);
        fireTask.put("state", currentState);
        fireTask.put("projectTitle", task.getProjecTitle());

        taskReference.collection("tasks")
                .document(sTask).set(fireTask);


        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().update(task);
        return null;
      }
      @Override
      protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG);
        finish();
        startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
      }
    }

    UpdateTask ut = new UpdateTask();
    ut.execute();
  }

  private void deleteTask(final Task task){
    class DeleteTask extends AsyncTask<Void, Void, Void>{

      @Override
      protected Void doInBackground(Void... voids){
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().delete(task);
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG);
        finish();
        startActivity(new Intent(UpdateTaskActivity.this, ProjectActivity.class));
      }
    }

    DeleteTask dt = new DeleteTask();
    dt.execute();
  }

}
