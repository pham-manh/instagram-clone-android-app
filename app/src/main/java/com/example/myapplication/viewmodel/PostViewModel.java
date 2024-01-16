package com.example.myapplication.viewmodel;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.utils.Activity;
import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;

public class PostViewModel extends BaseObservable {
    public static final int IMAGE_VIEW_SRC_DEFAULT = R.mipmap.ic_launcher;

    private SocialAutoCompleteTextView description;

    public void setDescription(SocialAutoCompleteTextView description) {
        this.description = description;
    }

    public MutableLiveData<Activity> activityMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Uri> imageUri = new MutableLiveData<>();
    private final ActivityResultLauncher<PickVisualMediaRequest> pickVisualMedia;

    public PostViewModel(ActivityResultLauncher<PickVisualMediaRequest> pickMedia) {
        pickVisualMedia = pickMedia;
    }

    @Bindable
    public String getDescription() {
        return description.getText().toString();
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public MutableLiveData<Activity> getActivityMutableLiveData() {
        return activityMutableLiveData;
    }

    @Bindable
    public MutableLiveData<Uri> getImageUri() {
        return imageUri;
    }

    public void updateImgUri(Uri uri) {
        imageUri.setValue(uri);
        notifyPropertyChanged(BR.imageUri);
    }

    @BindingAdapter("android:src")
    public static void setImageURI(ImageView view, Uri uri) {
        if (uri != null) {
            view.setImageURI(uri);
        } else {
            view.setImageResource(IMAGE_VIEW_SRC_DEFAULT);
        }
    }

    public void pickImg() {
        pickVisualMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    public void closePostActivity() {
        activityMutableLiveData.setValue(Activity.MAIN_ACTIVITY);
    }

    public void uploadPost() {
        Log.i("uploadPost", getDescription());
    }

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
//        private String getFileExtension(Uri imageUri) {
//        return MimeTypeMap.getSingleton()
//                .getExtensionFromMimeType(
//                        this.getContentResolver().getType(imageUri)
//                );
//        }
}
