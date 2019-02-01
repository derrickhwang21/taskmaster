package com.hwang.taskmaster;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ProjectActivity extends AppCompatActivity {

  private FloatingActionButton buttonAddTask;
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

    buttonAddTask = findViewById(R.id.floating_button_add);
    buttonAddTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(ProjectActivity.this, AddProjectActivity.class);
        startActivity(intent);
      }
    });
//    getTasks();
  }

//  private void getTasks(){
//    class GetTasks extends AsyncTask<Void, Void, List<Task>> {
//
//      @Override
//      protected List<Project> doInBackground(Void... voids){
//        List<Project> projectList = Room.databaseBuilder().getInstance(getApplicationContext()).getAppDatabase().projectDao().getAll();
//        return projectList;
//      }
//
//      @Override
//      protected void onPostExecute(List<Project> projects){
//        super.onPostExecute(projects);
//        ProjectAdapter adapter = new ProjectAdapter(ProjectActivity.this, projects);
//        recyclerView.setAdapter(adapter);
//      }
//    }
//    GetTasks gt = new GetTasks();
//    gt.execute();
//  }

}
