package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setLoginModel(new LoginViewModel());
        activityLoginBinding.getLoginModel().getLoginStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginStatus) {
                if (loginStatus) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
        activityLoginBinding.getLoginModel().getLoginMessages().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String msg) {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}