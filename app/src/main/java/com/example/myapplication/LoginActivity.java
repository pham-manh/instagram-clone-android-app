package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTxtEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btn_login);
        TextView registerUser = findViewById(R.id.regiser_user);

        editTxtEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        mAuth = FirebaseAuth.getInstance();


        registerUser.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,
                RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)));

        btnLogin.setOnClickListener(view -> {
            String userEmail = editTxtEmail.getText().toString();
            String userPassword = editTextPassword.getText().toString();


            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Empty credentials!!", Toast.LENGTH_SHORT).show();
            } else if (userPassword.length() < 6) {
                Toast.makeText(LoginActivity.this, "Password is too short!", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(userEmail, userPassword);
            }
        });


    }

    private void loginUser(String userEmail, String userPassword) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(
                        authTask -> {
                            if (authTask.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        })
                .addOnFailureListener(failTask -> Toast.makeText(LoginActivity.this,
                        failTask.getMessage(),
                        Toast.LENGTH_SHORT).show()
                );
    }
}