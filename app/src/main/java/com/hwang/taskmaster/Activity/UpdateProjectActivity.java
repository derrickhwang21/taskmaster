package com.hwang.taskmaster.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;


import com.hwang.taskmaster.Database.Project;
import com.hwang.taskmaster.Database.ProjectDatabaseClient;
import com.hwang.taskmaster.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



public class UpdateProjectActivity extends AppCompatActivity {

  private EditText editProjectTitle, editProjectDescription;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_project);

    editProjectTitle = findViewById(R.id.editProjectActivityTitle);
    editProjectDescription = findViewById(R.id.editProjectActivtyDescription);


    final Project project = (Project) getIntent().getSerializableExtra("project");

    loadProject(project);

    // Updated Listener
    findViewById(R.id.button_update_project).setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view){
        Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
        updateProject(project);
      }
    });

    // Delete Listener
    findViewById(R.id.button_delete_project).setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProjectActivity.this);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            deleteProject(project);
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

  private void loadProject(Project project){

    editProjectTitle.setText(project.getTitle());
    editProjectDescription.setText(project.getDescription());

  }



  private void updateProject(final Project project){
    final String sProject = editProjectTitle.getText().toString().trim();
    final String sPDesc = editProjectDescription.getText().toString().trim();

    if(sProject.isEmpty()){
      editProjectTitle.setError("Project Required");
      editProjectTitle.requestFocus();
      return;
    }
    if(sProject.isEmpty()){
      editProjectDescription.setError("Description Required");
      editProjectDescription.requestFocus();
      return;
    }


    class UpdateProject extends AsyncTask<Void, Void, Void> {
      @Override
      protected Void doInBackground(Void... voids){
        project.setTitle(sProject);
        project.setDescription(sPDesc);


        ProjectDatabaseClient.getInstance(getApplicationContext()).getProjectDatabase().projectDao().updateProject(project);
        return null;
      }
      @Override
      protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG);
        finish();
        startActivity(new Intent(UpdateProjectActivity.this, UpdateProjectActivity.class));
      }
    }

    UpdateProject up = new UpdateProject();
    up.execute();
  }

  private void deleteProject(final Project project){
    class DeleteProject extends AsyncTask<Void, Void, Void>{

      @Override
      protected Void doInBackground(Void... voids){
        ProjectDatabaseClient.getInstance(getApplicationContext()).getProjectDatabase().projectDao().deleteProject(project);
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG);
        finish();
        startActivity(new Intent(UpdateProjectActivity.this, UpdateProjectActivity.class));
      }
    }

    DeleteProject dp = new DeleteProject();
    dp.execute();
  }
}
