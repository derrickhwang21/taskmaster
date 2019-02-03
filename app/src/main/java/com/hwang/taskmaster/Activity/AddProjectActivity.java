package com.hwang.taskmaster.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hwang.taskmaster.Database.DatabaseClient;
import com.hwang.taskmaster.Database.Project;
import com.hwang.taskmaster.Database.ProjectDatabaseClient;
import com.hwang.taskmaster.Database.Task;
import com.hwang.taskmaster.R;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.opencensus.tags.Tag;

public class AddProjectActivity extends AppCompatActivity {


  private EditText editTextTitleProject;
  private EditText editTextDescriptionProject;
  private static final String TAG = "AddProjectActivity";




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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference projects = db.collection("project");
        Map<String, Object> fProjectObject = new HashMap<>();
        fProjectObject.put("title", saveTitle);
        fProjectObject.put("desc", saveDescription);
        projects.document(saveTitle).set(fProjectObject);


//        db.collection("project")
//                .add(fProjectObject)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                  @Override
//                  public void onSuccess(DocumentReference documentReference) {
//                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                  }
//                })
//                .addOnFailureListener(new OnFailureListener(){
//                  @Override
//                  public void onFailure(@NonNull Exception e){
//                    Log.w(TAG, "Error adding document", e);
//                  }
//                });
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
