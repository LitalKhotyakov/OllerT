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

/**
 * A fragment representing a list of Items.
 */
public class TasksListFragment extends Fragment {
    private  static final String TAG = "ItemFragment";

    private List<OllertTask> ollertTasks;
    private NavigatorCallBack navigatorCallBack;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private TasksListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
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
        getAllTasksFirebase();
        ollertTasks.add(new OllertTask("lital", "go home", new Date()));
        ollertTasks.add(new OllertTask("dany", "go home2", new Date()));
        ollertTasks.add(new OllertTask("vova", "go home3", new Date()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new TasksRecyclerViewAdapter(ollertTasks));
        }
        return view;
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
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}