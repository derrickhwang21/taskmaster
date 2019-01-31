package com.hwang.taskmaster;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

  private FloatingActionButton buttonAddTask;
  private RecyclerView recyclerView;
  /**
   * TODO refactor this into a file to reference and incremement in the future if necessary
   */
  private static final int RC_SIGN_IN = 3742;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = findViewById(R.id.recyclerview_tasks);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    buttonAddTask = findViewById(R.id.floating_button_add);
    buttonAddTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
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
        TasksAdapter adapter = new TasksAdapter(MainActivity.this, tasks);
        recyclerView.setAdapter(adapter);
      }
    }
    GetTasks gt = new GetTasks();
    gt.execute();
  }

  public void onSignInButtonPressed(View v){
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
    startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
            RC_SIGN_IN);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      IdpResponse response = IdpResponse.fromResultIntent(data);

      if (resultCode == RESULT_OK) {
        // Successfully signed in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView welcomeView = findViewById(R.id.welcomView);
        welcomeView.setText(user.getDisplayName());
        // ...
      } else {
        // Sign in failed. If response is null the user canceled the
        // sign-in flow using the back button. Otherwise check
        // response.getError().getErrorCode() and handle the error.
        // ...
      }
    }
  }


}
