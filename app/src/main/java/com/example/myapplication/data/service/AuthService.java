package com.example.myapplication.data.service;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

public class AuthService {

    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private boolean isValid;
    private String msg;

    public AuthService() {
        mAuth = FirebaseAuth.getInstance();
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private boolean isValidEmailPassword(String email, String password) {
        if (!isValidEmail(email)) {
            this.setMsg("Email is not valid!!");
            return false;
        }
        if (!isValidPassword(password)) {
            this.setMsg("Password is to short!!");
            return false;
        }
        return true;
    }

    public void loginUser(String email, String password, OnCompleteListener onCompleteListener) {
        if (isValidEmailPassword(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            authTask -> {
                                if (authTask.isSuccessful()) {
                                    onCompleteListener.onSuccess();
                                }
                            })
                    .addOnFailureListener(failTask ->
                            {
                                onCompleteListener.onFailure(failTask.getMessage());
                            }
                    );
        } else {
            onCompleteListener.onFailure(this.getMsg());
        }
    }

    public interface OnCompleteListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}

