package com.hwang.taskmaster;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class AddProjectActivity extends AppCompatActivity {

  private FloatingActionButton buttonAddTask;
  private RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_project);
    recyclerView = findViewById(R.id.recyclerview_tasks);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    TextView welcomeViewProject = findViewById(R.id.welcomeAddProject);
    Intent intent = getIntent();
    String displayName = intent.getStringExtra("DISPLAY_NAME");
    displayName = displayName == null ? "UNKOWN" : displayName;
    welcomeViewProject.setText("Hello: " + displayName);

    buttonAddTask = findViewById(R.id.floating_button_add);
    buttonAddTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(AddProjectActivity.this, AddTaskActivity.class);
        startActivity(intent);
      }
    });
    getTasks();
  }

  private void getTasks(){
    class GetTasks extends AsyncTask<Void, Void, List<Task>> {

      @Override
      protected List<Task> doInBackground(Void... voids){
        List<Task> taskList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().getAll();
        return taskList;
      }

      @Override
      protected void onPostExecute(List<Task> tasks){
        super.onPostExecute(tasks);
        TasksAdapter adapter = new TasksAdapter(AddProjectActivity.this, tasks);
        recyclerView.setAdapter(adapter);
      }
    }
    GetTasks gt = new GetTasks();
    gt.execute();
  }

}
