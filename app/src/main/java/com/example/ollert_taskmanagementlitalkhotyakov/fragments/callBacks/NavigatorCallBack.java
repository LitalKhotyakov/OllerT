package com.example.ollert_taskmanagementlitalkhotyakov.fragments.callBacks;

public interface NavigatorCallBack {

    enum ScreenName {
        TASK_LIST,
        CREATE_TASK

    }

    void navigateTo(ScreenName screenName);

}



