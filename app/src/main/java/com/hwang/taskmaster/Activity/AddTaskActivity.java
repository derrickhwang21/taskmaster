package com.hwang.taskmaster.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hwang.taskmaster.Database.DatabaseClient;
import com.hwang.taskmaster.Database.Project;
import com.hwang.taskmaster.Database.ProjectDatabaseClient;
import com.hwang.taskmaster.Database.Task;
import com.hwang.taskmaster.R;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class AddTaskActivity extends AppCompatActivity {


  private EditText editTextTitle;
  private EditText editTextDescription;
  private EditText editTextFinishBy;
  private String projectTitle;
  private String projectDescription;
  private static final String TAG = "AddTaskActivity";




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);

    projectTitle = getIntent().getStringExtra("project");
    editTextTitle = findViewById(R.id.editTextTask);
    editTextDescription = findViewById(R.id.editTextDesc);
    editTextFinishBy = findViewById(R.id.editTextFinishBy);

    findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view){
        saveTask();
      }
    });
  }

  private void saveTask(){

    final String saveTitle = editTextTitle.getText().toString().trim();
    final String saveDescription = editTextDescription.getText().toString().trim();
    final String saveFinishBy = editTextFinishBy.getText().toString().trim();

    if(saveTitle.isEmpty()){
      editTextTitle.setError("Task Required");
      editTextTitle.requestFocus();
      return;
    }

    if(saveDescription.isEmpty()){
      editTextDescription.setError("Description Required");
      editTextDescription.requestFocus();
      return;
    }

    if(saveFinishBy.isEmpty()){
      editTextFinishBy.setError("Finish By Require");
      editTextFinishBy.requestFocus();
      return;
    }

    class Savetask extends AsyncTask<Void, Void, Void> {
      @Override
      protected Void doInBackground(Void... voids){

        Task task = new Task();

        task.setTitle(saveTitle);
        task.setDescription(saveDescription);
        task.setFinishBy(saveFinishBy);
        task.setTaskState(null);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference taskReference = db.collection("project").document(projectTitle);
        Map<String, Object> fireTask = new HashMap<>();
        fireTask.put("name", saveTitle);
        fireTask.put("description", saveDescription);
        fireTask.put("finishBy", saveFinishBy);
        fireTask.put("state", null);

        taskReference.collection("tasks")
                .document(saveTitle).set(fireTask);

        
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().add(task);
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        finish();
        startActivity(new Intent(getApplicationContext(), TaskActivity.class));
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
      }
    }

    Savetask st = new Savetask();
    st.execute();
  }
}
