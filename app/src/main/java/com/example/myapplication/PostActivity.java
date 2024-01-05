package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;
import com.theartofdev.edmodo.cropper.CropImage;

public class PostActivity extends AppCompatActivity {

    private ImageView closeBtn;
    private ImageView imgAdded;
    private TextView postBtn;
    private SocialAutoCompleteTextView description;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    CropImage.activity(uri).start(this);
                }
            });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imgAdded.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                cropImageErrorMessage();
            }
        }
    }

    private void pickImg() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    private void cropImageErrorMessage() {
        Toast.makeText(this, "Fail to upload Image!", Toast.LENGTH_SHORT).show();
    }
}