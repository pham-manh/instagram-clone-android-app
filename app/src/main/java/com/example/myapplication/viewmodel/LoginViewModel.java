package com.example.myapplication.viewmodel;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.service.AuthService;
import com.example.myapplication.data.service.AuthService.OnCompleteListener;

public class LoginViewModel extends BaseObservable {
    private String email, password;
    public MutableLiveData<String> loginMessages = new MutableLiveData<>();
    public MutableLiveData<Boolean> loginStatus = new MutableLiveData<>(false);

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

    public MutableLiveData<String> getLoginMessages() {
        return loginMessages;
    }

    public MutableLiveData<Boolean> getLoginStatus() {
        return loginStatus;
    }

    public void loginUser() {
        AuthService authService = new AuthService();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            loginMessages.setValue("Empty credentials!!");
        } else {
            authService.loginUser(email, password, new OnCompleteListener() {
                @Override
                public void onSuccess() {
                    loginStatus.setValue(true);
                }

                @Override
                public void onFailure(String errorMessage) {
                    loginMessages.setValue(errorMessage);
                }
            });
        }
    }
}
