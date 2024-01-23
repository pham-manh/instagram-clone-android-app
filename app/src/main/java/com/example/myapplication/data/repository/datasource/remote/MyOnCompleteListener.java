package com.example.myapplication.data.repository.datasource.remote;

public interface MyOnCompleteListener {
    void onSuccess(String successMessage);

    void onFailure(String errorMessage);
}
