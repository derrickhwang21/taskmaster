package com.hwang.taskmaster.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwang.taskmaster.Database.Task;
import com.hwang.taskmaster.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

  private Context myContext;
  private List<Task> taskList;


  public TasksAdapter(Context myContext, List<Task> taskList){
    this.myContext = myContext;
    this.taskList = taskList;
  }

  @Override
  public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    View view = LayoutInflater.from(myContext).inflate(R.layout.recyclerview_projects, parent, false);
    return new TasksViewHolder(view);
  }

  @Override
  public void onBindViewHolder(TasksViewHolder holder, int position){
    Task t = taskList.get(position);


    holder.textViewTitle.setText(t.getTitle());
    holder.textViewDescription.setText(t.getDescription());
    holder.textViewFinishBy.setText(t.getFinishBy());
    holder.textViewStatus.setText(t.getState().value);

  }

  @Override
  public int getItemCount(){
    return taskList.size();
  }

  class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textViewStatus, textViewTitle, textViewDescription, textViewFinishBy;

    public TasksViewHolder(View itemView){
      super(itemView);

      textViewStatus = itemView.findViewById(R.id.textViewStatus);
      textViewTitle = itemView.findViewById(R.id.textViewTask);
      textViewDescription = itemView.findViewById(R.id.textViewDesc);
      textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      Task task = taskList.get(getAdapterPosition());

      Intent intent = new Intent(myContext, UpdateTaskActivity.class);
      intent.putExtra("task", task);

      myContext.startActivity(intent);
    }
  }
}
