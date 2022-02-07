package com.example.ollert_taskmanagementlitalkhotyakov;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ollert_taskmanagementlitalkhotyakov.objects.OllertTask;
import com.example.ollert_taskmanagementlitalkhotyakov.databinding.TaskItemBinding;

import java.util.List;


public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder> {

    private final List<OllertTask> ollertTasks;

    public TasksRecyclerViewAdapter(List<OllertTask> items) {
        ollertTasks = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.myOllertTask = ollertTasks.get(position);
        holder.taskTitleView.setText(ollertTasks.get(position).getTask_name()+"");
        holder.taskContentView.setText(ollertTasks.get(position).getTask_content()+"");
        holder.taskDate.setText(ollertTasks.get(position).getTask_date()+"");
    }

    @Override
    public int getItemCount() {
        return ollertTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView taskTitleView;
        public final TextView taskContentView;
        public final TextView taskDate;
        public OllertTask myOllertTask;

        public ViewHolder(TaskItemBinding binding) {
            super(binding.getRoot());
            taskTitleView = binding.taskName;
            taskContentView = binding.taskContent;
            taskDate = binding.taskDate;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + taskContentView.getText() + "'";
        }
    }
}