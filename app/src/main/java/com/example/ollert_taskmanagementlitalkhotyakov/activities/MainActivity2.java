package com.example.ollert_taskmanagementlitalkhotyakov.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.ollert_taskmanagementlitalkhotyakov.R;
import com.example.ollert_taskmanagementlitalkhotyakov.fragments.CreateTaskFragment;
import com.example.ollert_taskmanagementlitalkhotyakov.fragments.TasksListFragment;
import com.example.ollert_taskmanagementlitalkhotyakov.fragments.callBacks.NavigatorCallBack;
import com.example.ollert_taskmanagementlitalkhotyakov.objects.OllertTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    private  static final int LIMIT = 50;
    private  static final String TAG = "MainActivity2";
    public static final String COLLECTION_NAME = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navigatorCallBack.navigateTo(NavigatorCallBack.ScreenName.TASK_LIST,null);

//        OllertTask ollertTask = new OllertTask("lital", "go home2", new Date());
//        setTaskToFirestore(task);
//        getAllTasksFirebase();


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



 private NavigatorCallBack navigatorCallBack = new NavigatorCallBack() {
     @Override
     public void navigateTo(ScreenName screenName, OllertTask task) {
         switch (screenName){
             case TASK_LIST:
                 getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu,
                         TasksListFragment.newInstance(navigatorCallBack)).commit();
                 break;
             case CREATE_TASK:
                 getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu,
                         CreateTaskFragment.newInstance(navigatorCallBack,task)).commit();
                 break;
         }
     }
 };
}