package com.example.ollert_taskmanagementlitalkhotyakov.fragments;

import static com.example.ollert_taskmanagementlitalkhotyakov.activities.MainActivity2.COLLECTION_NAME;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ollert_taskmanagementlitalkhotyakov.R;
import com.example.ollert_taskmanagementlitalkhotyakov.fragments.callBacks.NavigatorCallBack;
import com.example.ollert_taskmanagementlitalkhotyakov.objects.OllertTask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CreateTaskFragment extends Fragment {
    private static final String TAG = "CreateTaskFragment";



    private NavigatorCallBack navigatorCallBack;
    private OllertTask exsistingTask;
    private MaterialButton button;
    private TextInputLayout nameEditText;
    private TextInputLayout descriptionEditText;
    private Switch aSwitch;
    private Button btnDatePicker;
    private TextInputLayout txtDate;
    private int mYear, mMonth, mDay;
    private Date taskDate = new Date();



    private CreateTaskFragment() {
        // Required empty public constructor
    }


    public static CreateTaskFragment newInstance(NavigatorCallBack navigatorCallBack, OllertTask task) {
        CreateTaskFragment fragment = new CreateTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.navigatorCallBack = navigatorCallBack;
        fragment.exsistingTask = task;
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
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);
        nameEditText = view.findViewById(R.id.editTextTextPersonName3);
        descriptionEditText = view.findViewById(R.id.editTextTextPersonName4);
        aSwitch = view.findViewById(R.id.is_done_swich);
        button = view.findViewById(R.id.button);
        btnDatePicker = view.findViewById(R.id.btn_date);
        txtDate = view.findViewById(R.id.in_date);

        if (exsistingTask != null){
            nameEditText.getEditText().setText(exsistingTask.getTask_name());
            nameEditText.getEditText().setFocusable(false);
            nameEditText.getEditText().setEnabled(false);
            descriptionEditText.getEditText().setText(exsistingTask.getTask_content());
            txtDate.getEditText().setText(exsistingTask.getTask_date().getDay() + "-" + exsistingTask.getTask_date().getMonth() + "-" + exsistingTask.getTask_date().getYear() );
            aSwitch.setChecked(exsistingTask.getDone());

        }

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btnDatePicker) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    txtDate.getEditText().setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    taskDate = new GregorianCalendar(year,monthOfYear - 1,dayOfMonth).getTime();

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getEditText().getText().toString();
                String description = descriptionEditText.getEditText().getText().toString();


                OllertTask ollertTask = new OllertTask(name, description, taskDate, aSwitch.isChecked());
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
                        navigatorCallBack.navigateTo(NavigatorCallBack.ScreenName.TASK_LIST,null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(getContext(), "Error writing document", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}