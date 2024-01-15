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
import com.example.myapplication.viewmodel.utils.Activity;
import com.example.myapplication.viewmodel.utils.ActivityUtils;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityLoginBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityBinding.setLoginModel(new LoginViewModel());

        activityBinding.getLoginModel()
                .getActivityActionLiveData()
                .observe(this, new Observer<Activity>() {
                    @Override
                    public void onChanged(Activity activity) {
                        Intent intent = new Intent(
                                LoginActivity.this,
                                ActivityUtils.getActivityClass(activity)
                        );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });

        activityBinding.getLoginModel()
                .getViewMessage()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String msg) {
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}