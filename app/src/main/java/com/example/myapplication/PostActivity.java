package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

        imgAdded.setOnClickListener(view -> pickImg());
    }

    private ActivityResultLauncher<Intent> takeImage =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri uri = result.getData().getData();
                            imgAdded.setImageURI(uri);
                        }
                    });

    private void pickImg() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeImage.launch(intent);
    }

}