package com.example.myapplication.data.service;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class AuthService {

    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference mRootRef = FirebaseDatabase
            .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference();

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

    public void loginUser(String email, String password, OnAuthServiceCompleteListener onAuthServiceCompleteListener) {
        if (isValidEmailPassword(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            authTask -> {
                                if (authTask.isSuccessful()) {
                                    onAuthServiceCompleteListener.onSuccess("Login Success !!");
                                }
                            })
                    .addOnFailureListener(failTask ->
                            {
                                onAuthServiceCompleteListener.onFailure(failTask.getMessage());
                            }
                    );
        } else {
            onAuthServiceCompleteListener.onFailure(this.getMsg());
        }
    }

    public void registerUser(String username, String name, String email, String password,
                             OnAuthServiceCompleteListener onAuthServiceCompleteListener)
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

                                mRootRef.child("Users")
                                        .child(uid)
                                        .setValue(newUser)
                                        .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        onAuthServiceCompleteListener.onSuccess(
                                                                "Update the Profile for better experience"
                                                        );
                                                    }
                                                }
                                        );
                            })
                    .addOnFailureListener(exception -> {
                        onAuthServiceCompleteListener.onFailure(exception.getMessage());
                    });
        } else {
            onAuthServiceCompleteListener.onFailure(this.getMsg());
        }

    }

    public static void signOutCurrentUser() {
        mAuth.signOut();
    }

    public interface OnAuthServiceCompleteListener {
        void onSuccess(String successMessage);

        void onFailure(String errorMessage);
    }
}

