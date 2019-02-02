package com.hwang.taskmaster.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hwang.taskmaster.Database.Project;
import com.hwang.taskmaster.Database.ProjectDatabaseClient;
import com.hwang.taskmaster.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ProjectActivity extends AppCompatActivity {

  private FloatingActionButton buttonAddProject;
  private RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project);
    recyclerView = findViewById(R.id.recyclerview_projects);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    TextView welcomeViewProject = findViewById(R.id.welcomeAddProject);
    Intent intent = getIntent();
    String displayName = intent.getStringExtra("DISPLAY_NAME");
    displayName = displayName == null ? "UNKOWN" : displayName;
    welcomeViewProject.setText("Hello: " + displayName + " Go ahead and add your first project!");

    buttonAddProject = findViewById(R.id.floating_button_add_project);
    buttonAddProject.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(ProjectActivity.this, AddProjectActivity.class);
        startActivity(intent);
      }
    });
    getProjects();
  }

  private void getProjects(){
    class GetProjects extends AsyncTask<Void, Void, List<Project>> {

      @Override
      protected List<Project> doInBackground(Void... voids){
        List<Project> projectList = ProjectDatabaseClient.getInstance(getApplicationContext()).getProjectDatabase().projectDao().getAll();
        return projectList;
      }

      @Override
      protected void onPostExecute(List<Project> projects){
        super.onPostExecute(projects);
        ProjectAdapter adapter = new ProjectAdapter(ProjectActivity.this, projects);
        recyclerView.setAdapter(adapter);
      }
    }
    GetProjects gp = new GetProjects();
    gp.execute();
  }

}
