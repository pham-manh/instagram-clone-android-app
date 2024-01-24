package com.example.myapplication.utils.callbacks;

public interface MyOnCompleteListener {
    void onSuccess(String successMessage);

    void onFailure(String errorMessage);
}
