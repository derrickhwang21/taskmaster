package com.hwang.taskmaster.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.hwang.taskmaster.Database.Project;
import com.hwang.taskmaster.R;

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
  public void onBindViewHolder(final ProjectViewHolder holder, int position){
    Project t = projectList.get(position);
    holder.projectViewTitles.setText(t.getTitle());
    holder.projectViewOption.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        //creating a popup menu
        PopupMenu popup = new PopupMenu(myContext, holder.projectViewOption);
        //inflating menu from xml resource
        popup.inflate(R.menu.options_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
              case R.id.menu1:
                Project project = projectList.get(holder.getAdapterPosition());

                Intent intent = new Intent(myContext, UpdateProjectActivity.class);
                intent.putExtra("project", project);


                myContext.startActivity(intent);
                return true;
              default:
                return false;
            }
          }
        });
        //displaying the popup
        popup.show();

      }
    });

  }

  @Override
  public int getItemCount(){
    return projectList.size();
  }

  class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView projectViewTitles, projectViewOption;

    public ProjectViewHolder(View itemView){
      super(itemView);
      projectViewTitles = itemView.findViewById(R.id.textViewProject);
      projectViewOption = itemView.findViewById(R.id.textViewOptions);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      Project project = projectList.get(getAdapterPosition());

      Intent intent = new Intent(myContext, TaskActivity.class);
      intent.putExtra("project", project.title);



      myContext.startActivity(intent);
    }
  }
}
