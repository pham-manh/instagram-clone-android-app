package com.example.myapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.example.myapplication.viewmodel.RegisterViewModel;
import com.example.myapplication.utils.Activity;
import com.example.myapplication.utils.ActivityUtils;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActivityRegisterBinding activityBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_register);
        activityBinding.setRegisterViewModel(new RegisterViewModel(getApplicationContext()));

        activityBinding.getRegisterViewModel()
                .getActivityMutableLiveData()
                .observe(this, new Observer<Activity>() {
                    @Override
                    public void onChanged(Activity activity) {
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                ActivityUtils.getActivityClass(activity)
                        );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
        activityBinding.getRegisterViewModel()
                .getViewMessage()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String msg) {
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}