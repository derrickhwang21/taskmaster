package com.hwang.taskmaster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.hwang.taskmaster.Task.State.ACCEPTED;
import static com.hwang.taskmaster.Task.State.ASSIGNED;
import static com.hwang.taskmaster.Task.State.AVAILABLE;
import static com.hwang.taskmaster.Task.State.FINISHED;

public class UpdateTaskActivity extends AppCompatActivity {

  private EditText editTextTitle, editTextDescription, editTextFinishBy;
  private CheckBox checkBoxFinished;
  private RadioGroup checkRadioState;
  private RadioButton available, assigned, accepted, finished;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_task);

    editTextTitle = findViewById(R.id.editTextTask);
    editTextDescription = findViewById(R.id.editTextDesc);
    editTextFinishBy = findViewById(R.id.editTextFinishBy);
    checkBoxFinished = findViewById(R.id.checkBoxFinished);
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
  }

  private void loadTask(Task task){

    editTextTitle.setText(task.getTitle());
    editTextDescription.setText(task.getDescription());
    editTextFinishBy.setText(task.getFinishBy());
    switch(task.State.name()){
        case "AVAILABLE":
          available.setChecked(true);
          assigned.setChecked(false);
          accepted.setChecked(false);
          finished.setChecked(false);
          break;
        case "ASSIGNED":
          available.setChecked(false);
          assigned.setChecked(true);
          accepted.setChecked(false);
          finished.setChecked(false);
          break;
        case "ACCEPTED":
          available.setChecked(false);
          assigned.setChecked(false);
          accepted.setChecked(true);
          finished.setChecked(false);
          break;
        case "FINISHED":
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
        checkRadioState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.finished){
              task.setState(FINISHED);
            }else if(checkedId == R.id.assigned){
              task.setState(ASSIGNED);
            }else if(checkedId == R.id.accepted){
              task.setState(ACCEPTED);
            }else{
              task.setState(AVAILABLE);
            }
          }
        });

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
        startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
      }
    }

    DeleteTask dt = new DeleteTask();
    dt.execute();
  }

}
