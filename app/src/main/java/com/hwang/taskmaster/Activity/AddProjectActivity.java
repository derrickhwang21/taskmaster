package com.hwang.taskmaster.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hwang.taskmaster.Database.DatabaseClient;
import com.hwang.taskmaster.Database.Project;
import com.hwang.taskmaster.Database.ProjectDatabaseClient;
import com.hwang.taskmaster.Database.Task;
import com.hwang.taskmaster.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddProjectActivity extends AppCompatActivity {


  private EditText editTextTitleProject;
  private EditText editTextDescriptionProject;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_project);

    editTextTitleProject = findViewById(R.id.editTextProject);
    editTextDescriptionProject = findViewById(R.id.editTextDescProject);


    findViewById(R.id.button_save_project).setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view){
        saveProject();
      }
    });
  }

  private void saveProject(){
    final String saveTitle = editTextTitleProject.getText().toString().trim();
    final String saveDescription = editTextDescriptionProject.getText().toString().trim();

    if(saveTitle.isEmpty()){
      editTextTitleProject.setError("Project Required");
      editTextTitleProject.requestFocus();
      return;
    }

    if(saveDescription.isEmpty()){
      editTextDescriptionProject.setError("Description Required");
      editTextDescriptionProject.requestFocus();
      return;
    }


    class SaveProject extends AsyncTask<Void, Void, Void> {
      @Override
      protected Void doInBackground(Void... voids){
        Project project = new Project();
        project.setTitle(saveTitle);
        project.setDescription(saveDescription);


        ProjectDatabaseClient.getInstance(getApplicationContext()).getProjectDatabase().projectDao().insertProject(project);
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        finish();
        startActivity(new Intent(getApplicationContext(), ProjectActivity.class));
        Toast.makeText(getApplicationContext(), "Created", Toast.LENGTH_LONG).show();
      }
    }

    SaveProject sp = new SaveProject();
    sp.execute();
  }
}
