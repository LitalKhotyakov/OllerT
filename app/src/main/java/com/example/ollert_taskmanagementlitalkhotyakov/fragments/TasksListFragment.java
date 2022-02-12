package com.example.ollert_taskmanagementlitalkhotyakov.fragments;

import static com.example.ollert_taskmanagementlitalkhotyakov.activities.MainActivity2.COLLECTION_NAME;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.ollert_taskmanagementlitalkhotyakov.TasksRecyclerViewAdapter;
import com.example.ollert_taskmanagementlitalkhotyakov.R;
import com.example.ollert_taskmanagementlitalkhotyakov.fragments.callBacks.NavigatorCallBack;
import com.example.ollert_taskmanagementlitalkhotyakov.objects.OllertTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * A fragment representing a list of Items.
 */
public class TasksListFragment extends Fragment {
    private static final String TAG = "ItemFragment";

    private List<OllertTask> ollertTasks;
    private NavigatorCallBack navigatorCallBack;
    private RecyclerView recyclerView;
    private Switch isDoneSwitch;
    private TasksRecyclerViewAdapter adapter;


    private TasksListFragment() {
    }

    public static TasksListFragment newInstance(NavigatorCallBack navigatorCallBack) {
        TasksListFragment fragment = new TasksListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.navigatorCallBack = navigatorCallBack;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ollertTasks = new ArrayList<>();
//        ollertTasks.add(new OllertTask("lital", "go home", new Date()));
//        ollertTasks.add(new OllertTask("dany", "go home2", new Date()));
//        ollertTasks.add(new OllertTask("vova", "go home3", new Date()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks_item_list, container, false);
        Button button = view.findViewById(R.id.add_task_button);
        button.setOnClickListener(onClickListener);

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        isDoneSwitch = view.findViewById(R.id.is_done_filter);
        isDoneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getAllTasksFirebase();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllTasksFirebase();
    }

    private void getAllTasksFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Date date;
                                boolean isDone;
                                try {
                                    date = new Date(document.getTimestamp("task_date").getSeconds() * 1000);
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                    date = new Date();
                                }

                                try {
                                    isDone = document.getBoolean("done");
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                    isDone = false;
                                }


                                OllertTask ollertTask = new OllertTask(document.getId(),
                                        document.getString("task_content"),
                                        date,
                                        isDone);
                                ollertTasks.add(ollertTask);
                            }
                            ollertTasks.removeIf(predicate);
                            adapter = new TasksRecyclerViewAdapter(ollertTasks, navigatorCallBack);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            navigatorCallBack.navigateTo(NavigatorCallBack.ScreenName.CREATE_TASK, null);
        }
    };

    Predicate<OllertTask> predicate = new Predicate<OllertTask>() {
        @Override
        public boolean test(OllertTask task) {
            return task.getDone() != isDoneSwitch.isChecked();
        }
    };
}