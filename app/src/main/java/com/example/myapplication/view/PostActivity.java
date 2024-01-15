package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityPostBinding;
import com.example.myapplication.utils.Activity;
import com.example.myapplication.utils.ActivityUtils;
import com.example.myapplication.viewmodel.PostViewModel;
import com.theartofdev.edmodo.cropper.CropImage;

public class PostActivity extends AppCompatActivity {
    private ActivityPostBinding activityPostBinding;
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

        activityPostBinding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        activityPostBinding.setPostViewModel(new PostViewModel(pickMedia));

        activityPostBinding.getPostViewModel()
                .getActivityMutableLiveData()
                .observe(this, new Observer<Activity>() {
                    @Override
                    public void onChanged(Activity activity) {
                        Intent intent = new Intent(
                                PostActivity.this,
                                ActivityUtils.getActivityClass(activity)
                        );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                activityPostBinding.getPostViewModel()
                        .updateImgUri(result.getUri());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                cropImageErrorMessage();
            }
        }
    }
    private void cropImageErrorMessage() {
        Toast.makeText(this, "Fail to upload Image!", Toast.LENGTH_SHORT).show();
    }
    //
//    private void uploadPost() {
//        if (imageUri != null) {
//            Log.i("Post", "Uploading");
//            StorageReference filePart = FirebaseStorage.getInstance("gs://instagram-clone-784ff.appspot.com")
//                    .getReference("Posts")
//                    .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
//            StorageTask<UploadTask.TaskSnapshot> storageTask = filePart.putFile(imageUri);
//            storageTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw Objects.requireNonNull(task.getException());
//                    }
//                    return filePart.getDownloadUrl();
//                }
//            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
//                Uri downloadURL = task.getResult();
//                imageUrl = downloadURL.toString();
//
//                DatabaseReference ref = FirebaseDatabase
//                        .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                        .getReference("Posts");
//                String postId = ref.push().getKey();
//
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("postId", postId);
//                map.put("imageUrl", imageUrl);
//                map.put("description", description.getText().toString());
//                map.put("publisher", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
//
//                ref.child(postId).setValue(map);
//
//                DatabaseReference mHashTagRef = FirebaseDatabase
//                        .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                        .getReference()
//                        .child("HashTags");
//                List<String> hashTags = description.getHashtags();
//                if (!hashTags.isEmpty()) {
//                    for (String tag : hashTags
//                    ) {
//                        map.clear();
//                        map.put("tag", tag.toLowerCase());
//                        map.put("postId", postId);
//                        mHashTagRef.child(tag.toLowerCase()).child(postId).setValue(map);
//                    }
//                }
//                startActivity(new Intent(PostActivity.this, MainActivity.class));
//                finish();
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//
//    private String getFileExtension(Uri imageUri) {
//        return MimeTypeMap.getSingleton()
//                .getExtensionFromMimeType(
//                        this.getContentResolver().getType(imageUri)
//                );
//    }
}