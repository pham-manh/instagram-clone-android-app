package com.example.myapplication.viewmodel;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.repository.datasource.remote.AuthService;
import com.example.myapplication.data.repository.datasource.remote.MyOnCompleteListener;
import com.example.myapplication.utils.Activity;

public class RegisterViewModel extends BaseObservable {
    private String username, name, email, password;
    public MutableLiveData<String> viewMessage = new MutableLiveData<>();
    public MutableLiveData<Activity> activityMutableLiveData = new MutableLiveData<>();

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public MutableLiveData<Activity> getActivityMutableLiveData() {
        return activityMutableLiveData;
    }

    public void toLogin() {
        activityMutableLiveData.setValue(Activity.LOGIN_ACTIVITY);
    }

    public void onRegister() {
        AuthService authService = new AuthService();
        if (TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(username)
                || TextUtils.isEmpty(name)) {
            viewMessage.setValue("Empty credentials!!");
        } else {
            authService.registerUser(username, name, email, password,
                    new MyOnCompleteListener() {
                        @Override
                        public void onSuccess(String successMessage) {
                            activityMutableLiveData.setValue(Activity.MAIN_ACTIVITY);
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

