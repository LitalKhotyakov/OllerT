package com.example.ollert_taskmanagementlitalkhotyakov;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        holder.update(ollertTasks.get(position));

    }

    @Override
    public int getItemCount() {
        return ollertTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TaskItemBinding binding;

        public ViewHolder(TaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void update(OllertTask myOllertTask) {
            binding.taskName.setText(myOllertTask.getTask_name()+"");
            binding.taskContent.setText(myOllertTask.getTask_content()+"");
            binding.taskDate.setText(myOllertTask.getTask_date()+"");
            if (!myOllertTask.getDone()){
                binding.taskIsDone.setVisibility(View.INVISIBLE);
            }
        }

    }
}