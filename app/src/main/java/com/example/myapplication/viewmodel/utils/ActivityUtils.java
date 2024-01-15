package com.example.myapplication.viewmodel.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.view.LoginActivity;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.RegisterActivity;
import com.example.myapplication.view.StartActivity;

public class ActivityUtils {

    public static Class<? extends AppCompatActivity> getActivityClass(Activity activity) {
        switch (activity) {
            case LOGIN_ACTIVITY:
                return LoginActivity.class;
            case REGISTER_ACTIVITY:
                return RegisterActivity.class;
            case MAIN_ACTIVITY:
                return MainActivity.class;
            default:
                return StartActivity.class;
        }
    }
}
