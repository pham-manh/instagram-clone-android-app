package com.example.myapplication.data.service;

public interface MyOnCompleteListener {
    void onSuccess(String successMessage);

    void onFailure(String errorMessage);
}
