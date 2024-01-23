package com.example.myapplication.data.repository.datasource.remote;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Objects;

public class AuthService {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
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

    public void loginUser(String email, String password,
                          MyOnCompleteListener myOnCompleteListener) {
        if (isValidEmailPassword(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            authTask -> {
                                if (authTask.isSuccessful()) {
                                    myOnCompleteListener.onSuccess("Login Success !!");
                                }
                            })
                    .addOnFailureListener(failTask ->
                            myOnCompleteListener.onFailure(failTask.getMessage())
                    );
        } else {
            myOnCompleteListener.onFailure(this.getMsg());
        }
    }

    public void registerUser(String username, String name, String email, String password,
                             MyOnCompleteListener myOnCompleteListener)
            throws NullPointerException {
        if (isValidEmailPassword(email, password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(
                            authResult -> {
                                String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                                HashMap<String, Object> newUser = new HashMap<>();
                                newUser.put("username", username);
                                newUser.put("name", name);
                                newUser.put("email", email);
                                newUser.put("password", password);
                                newUser.put("id", uid);
                                newUser.put("bio", "");
                                newUser.put("imageUrl", "");
                                StorageService
                                        .getRealTimeDatabase()
                                        .getReference()
                                        .child("Users")
                                        .child(uid)
                                        .setValue(newUser)
                                        .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        myOnCompleteListener.onSuccess(
                                                                "Update the Profile for better experience"
                                                        );
                                                    }
                                                }
                                        );
                            })
                    .addOnFailureListener(exception -> {
                        myOnCompleteListener.onFailure(exception.getMessage());
                    });
        } else {
            myOnCompleteListener.onFailure(this.getMsg());
        }
    }

    public static void signOutCurrentUser() {
        mAuth.signOut();
    }
}

