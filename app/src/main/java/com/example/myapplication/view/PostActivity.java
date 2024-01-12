package com.example.myapplication.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {

    private ImageView closeBtn;
    private ImageView imgAdded;
    private TextView postBtn;
    private Uri imageUri;
    private String imageUrl;
    private SocialAutoCompleteTextView description;
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
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
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Post", "Post is Click");
                uploadPost();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imgAdded.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                cropImageErrorMessage();
            }
        }
    }

    private void uploadPost() {
        if (imageUri != null) {
            Log.i("Post", "Uploading");
            StorageReference filePart = FirebaseStorage.getInstance("gs://instagram-clone-784ff.appspot.com")
                    .getReference("Posts")
                    .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            StorageTask<UploadTask.TaskSnapshot> storageTask = filePart.putFile(imageUri);
            storageTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return filePart.getDownloadUrl();
                }
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                Uri downloadURL = task.getResult();
                imageUrl = downloadURL.toString();

                DatabaseReference ref = FirebaseDatabase
                        .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("Posts");
                String postId = ref.push().getKey();

                HashMap<String, Object> map = new HashMap<>();
                map.put("postId", postId);
                map.put("imageUrl", imageUrl);
                map.put("description", description.getText().toString());
                map.put("publisher", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

                ref.child(postId).setValue(map);

                DatabaseReference mHashTagRef = FirebaseDatabase
                        .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference()
                        .child("HashTags");
                List<String> hashTags = description.getHashtags();
                if (!hashTags.isEmpty()) {
                    for (String tag : hashTags
                    ) {
                        map.clear();
                        map.put("tag", tag.toLowerCase());
                        map.put("postId", postId);
                        mHashTagRef.child(tag.toLowerCase()).child(postId).setValue(map);
                    }
                }
                startActivity(new Intent(PostActivity.this, MainActivity.class));
                finish();
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getFileExtension(Uri imageUri) {
        return MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(
                        this.getContentResolver().getType(imageUri)
                );
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