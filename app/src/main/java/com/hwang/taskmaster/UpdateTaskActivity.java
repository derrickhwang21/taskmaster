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
import android.widget.Toast;

public class UpdateTaskActivity extends AppCompatActivity {

  private EditText editTextTitle, editTextDescription, editTextFinishBy;
  private CheckBox checkBoxFinished;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_task);

    editTextTitle = findViewById(R.id.editTextTask);
    editTextDescription = findViewById(R.id.editTextDesc);
    editTextFinishBy = findViewById(R.id.editTextFinishBy);

    checkBoxFinished = findViewById(R.id.checkBoxFinished);

    final Task task = (Task) getIntent().getSerializableExtra("task");

    loadTask(task);

    findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view){
        Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
        updateTask(task);
      }
    });

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
