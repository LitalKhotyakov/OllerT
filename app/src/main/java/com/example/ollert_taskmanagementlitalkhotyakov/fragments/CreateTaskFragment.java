package com.example.ollert_taskmanagementlitalkhotyakov.fragments;

import static com.example.ollert_taskmanagementlitalkhotyakov.activities.MainActivity2.COLLECTION_NAME;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ollert_taskmanagementlitalkhotyakov.R;
import com.example.ollert_taskmanagementlitalkhotyakov.fragments.callBacks.NavigatorCallBack;
import com.example.ollert_taskmanagementlitalkhotyakov.objects.OllertTask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTaskFragment extends Fragment {
    private  static final String TAG = "CreateTaskFragment";



    private NavigatorCallBack navigatorCallBack;
    private Button button;
    private EditText nameEditText;
    private Switch aSwitch;

    private CreateTaskFragment() {
        // Required empty public constructor
    }


    public static CreateTaskFragment newInstance(NavigatorCallBack navigatorCallBack) {
        CreateTaskFragment fragment = new CreateTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.navigatorCallBack = navigatorCallBack;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_create_task, container, false);
        nameEditText = view.findViewById(R.id.editTextTextPersonName3);
        aSwitch = view.findViewById(R.id.is_done_swich);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                OllertTask ollertTask = new OllertTask(name,"test test blabla",new Date(),aSwitch.isChecked());
                setTaskToFirestore(ollertTask);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void setTaskToFirestore(OllertTask ollertTask) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_NAME).document(ollertTask.getTask_name())
                .set(ollertTask)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        navigatorCallBack.navigateTo(NavigatorCallBack.ScreenName.TASK_LIST);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(getContext(),"Error writing document",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}