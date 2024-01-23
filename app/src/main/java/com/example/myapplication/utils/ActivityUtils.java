package com.example.myapplication.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.activities.LoginActivity;
import com.example.myapplication.ui.activities.MainActivity;
import com.example.myapplication.ui.activities.PostActivity;
import com.example.myapplication.ui.activities.RegisterActivity;
import com.example.myapplication.ui.activities.StartActivity;

public class ActivityUtils {

    public static Class<? extends AppCompatActivity> getActivityClass(Activity activity) {
        switch (activity) {
            case LOGIN_ACTIVITY:
                return LoginActivity.class;
            case REGISTER_ACTIVITY:
                return RegisterActivity.class;
            case MAIN_ACTIVITY:
                return MainActivity.class;
            case POST_ACTIVITY:
                return PostActivity.class;
            default:
                return StartActivity.class;
        }
    }
}
