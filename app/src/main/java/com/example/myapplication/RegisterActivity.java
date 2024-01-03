package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button registerBtn = findViewById(R.id.btnRegister);
        TextView loginUser = findViewById(R.id.login_user);

        mRootRef = FirebaseDatabase
                .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        mAuth = FirebaseAuth.getInstance();

        loginUser.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this,
                LoginActivity.class)));

        registerBtn.setOnClickListener(view -> {
            String txtUsername = username.getText().toString();
            String txtName = name.getText().toString();
            String txtEmail = email.getText().toString();
            String txtPassword = password.getText().toString();

            if (TextUtils.isEmpty(txtUsername)
                    || TextUtils.isEmpty(txtName)
                    || TextUtils.isEmpty(txtEmail)
                    || TextUtils.isEmpty(txtPassword)) {
                Toast.makeText(RegisterActivity.this, "Empty credentials!!", Toast.LENGTH_SHORT).show();
            } else if (txtPassword.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password is too short!", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(txtUsername, txtName, txtEmail, txtPassword);
            }
        });
    }

    private void registerUser(String username, String name, String email, String password) throws NullPointerException {
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

                            mRootRef.child("Users")
                                    .child(uid)
                                    .setValue(newUser)
                                    .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this,
                                                                    "Update the Profile for better experience",
                                                                    Toast.LENGTH_SHORT)
                                                            .show();

                                                    Intent intent = new Intent(RegisterActivity.this,
                                                            MainActivity.class);

                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                            | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                    );
                        })
                .addOnFailureListener(exception -> Toast.makeText(RegisterActivity.this, exception.getMessage(),
                        Toast.LENGTH_SHORT).show()
                );
    }
}