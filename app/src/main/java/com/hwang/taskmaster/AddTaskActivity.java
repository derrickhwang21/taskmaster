package com.hwang.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {


  private EditText editTextTitle;
  private EditText editTextDescription;
  private EditText editTextFinishBy;
  Date currentTime = Calendar.getInstance().getTime();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);

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

        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().add(task);
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
      }
    }

    Savetask st = new Savetask();
    st.execute();
  }
}
