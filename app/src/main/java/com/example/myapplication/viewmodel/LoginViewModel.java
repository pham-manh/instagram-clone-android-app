package com.example.myapplication.viewmodel;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.repository.datasource.remote.AuthService;
import com.example.myapplication.data.repository.datasource.remote.MyOnCompleteListener;
import com.example.myapplication.utils.Activity;

public class LoginViewModel extends BaseObservable {
    private String email, password;
    public MutableLiveData<String> viewMessage = new MutableLiveData<>();
    public MutableLiveData<Activity> activityActionLiveData = new MutableLiveData<>();


    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MutableLiveData<String> getViewMessage() {
        return viewMessage;
    }

    public MutableLiveData<Activity> getActivityActionLiveData() {
        return activityActionLiveData;
    }
    public void toRegisterUser(){
        activityActionLiveData.setValue(Activity.REGISTER_ACTIVITY);
    }

    public void loginUser() {
        AuthService authService = new AuthService();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            viewMessage.setValue("Empty credentials!!");
        } else {
            authService.loginUser(email, password, new MyOnCompleteListener() {
                @Override
                public void onSuccess(String successMessage) {
                    activityActionLiveData.setValue(Activity.MAIN_ACTIVITY);
                    viewMessage.setValue(successMessage);
                }

                @Override
                public void onFailure(String errorMessage) {
                    viewMessage.setValue(errorMessage);
                }
            });
        }
    }
}
