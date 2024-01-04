package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;


public class PostActivity extends AppCompatActivity {

    private ImageView closeBtn;
    private ImageView imgAdded;
    private TextView postBtn;
    private SocialAutoCompleteTextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        closeBtn = findViewById(R.id.close);
        imgAdded = findViewById(R.id.image_add);
        postBtn = findViewById(R.id.post);
        description = findViewById(R.id.description);

        closeBtn.setOnClickListener(view -> {
            startActivity(new Intent(PostActivity.this, MainActivity.class));
            finish();
        });
    }
}