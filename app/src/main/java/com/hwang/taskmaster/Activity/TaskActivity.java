package com.hwang.taskmaster.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hwang.taskmaster.Database.DatabaseClient;
import com.hwang.taskmaster.Database.Project;
import com.hwang.taskmaster.Database.Task;
import com.hwang.taskmaster.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

  private FloatingActionButton buttonAddTask;
  private RecyclerView recyclerView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final String projectTitle = getIntent().getStringExtra("project");

    setContentView(R.layout.activity_task);
    recyclerView = findViewById(R.id.recyclerview_tasks);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    buttonAddTask = findViewById(R.id.floating_button_add);
    buttonAddTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
        intent.putExtra("project", projectTitle);
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
        TasksAdapter adapter = new TasksAdapter(TaskActivity.this, tasks);
        recyclerView.setAdapter(adapter);
      }
    }
    GetTasks gt = new GetTasks();
    gt.execute();
  }
}
