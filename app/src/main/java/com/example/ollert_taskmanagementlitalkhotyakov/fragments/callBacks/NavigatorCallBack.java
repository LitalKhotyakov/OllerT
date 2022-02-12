package com.example.ollert_taskmanagementlitalkhotyakov.fragments.callBacks;

import com.example.ollert_taskmanagementlitalkhotyakov.objects.OllertTask;

public interface NavigatorCallBack {

    enum ScreenName {
        TASK_LIST,
        CREATE_TASK

    }

    void navigateTo(ScreenName screenName, OllertTask task);

}



