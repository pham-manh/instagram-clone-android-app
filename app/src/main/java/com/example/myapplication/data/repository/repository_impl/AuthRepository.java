package com.example.myapplication.data.repository.repository_impl;

import android.content.Context;

import com.example.myapplication.data.repository.datasource.remote.AuthService;
import com.example.myapplication.utils.callbacks.MyOnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepository {
    private static final String TAG = "AuthRepository";
    private static final AuthRepository ourInstance = new AuthRepository();
    private final AuthService mAuth;

    public AuthRepository() {
        mAuth = AuthService.getInstance();
    }

    public static AuthRepository getInstance() {
        return ourInstance;
    }

    public void loginUser(String email, String password, MyOnCompleteListener myOnCompleteListener) {
        mAuth.loginUser(email, password, myOnCompleteListener);
    }

    public void registerUser(String username, String name, String email, String password,
                             MyOnCompleteListener myOnCompleteListener)
            throws NullPointerException {
        mAuth.registerUser(username, name, email, password, myOnCompleteListener);
    }
    public void signOutCurrentUser(){
        mAuth.signOutCurrentUser();
    }
}
