package com.hwang.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

  private Context myContext;
  private List<Project> projectList;


  public ProjectAdapter(Context myContext, List<Project> projectList){
    this.myContext = myContext;
    this.projectList = projectList;
  }

  @Override
  public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    View view = LayoutInflater.from(myContext).inflate(R.layout.recyclerview_projects, parent, false);
    return new ProjectViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ProjectViewHolder holder, int position){
    Project t = projectList.get(position);


    holder.projectViewTitles.setText(t.getTitle());

  }

  @Override
  public int getItemCount(){
    return projectList.size();
  }

  class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView projectViewTitles;

    public ProjectViewHolder(View itemView){
      super(itemView);


      projectViewTitles = itemView.findViewById(R.id.textViewProject);


      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      Project project = projectList.get(getAdapterPosition());

      Intent intent = new Intent(myContext, UpdateTaskActivity.class);
      intent.putExtra("project", project);


      myContext.startActivity(intent);
    }
  }
}
