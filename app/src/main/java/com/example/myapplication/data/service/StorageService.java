package com.example.myapplication.data.service;

import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StorageService {
    private static final String INSTANCE_FIREBASE_DATABASE =
            "https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private static final String INSTANCE_FIREBASE_STORAGE = "gs://instagram-clone-784ff.appspot.com";

    private ContentResolver contentResolver;

    public StorageService() {}

    public static FirebaseDatabase getRealTimeDatabase() {
        return FirebaseDatabase.getInstance(INSTANCE_FIREBASE_DATABASE);
    }

    public static FirebaseStorage getStorageDatabase() {
        return FirebaseStorage.getInstance(INSTANCE_FIREBASE_STORAGE);
    }

    private String getFileExtension(Uri imageUri) {
        return MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(
                        contentResolver.getType(imageUri)
                );
    }

    public void uploadPost(Uri imgUri, SocialAutoCompleteTextView description, String fileExtension,
                           MyOnCompleteListener onStorageServiceListener) {
        if (imgUri != null) {
            StorageReference filePart = getStorageDatabase().getReference("Posts")
                    .child(System.currentTimeMillis() + "." + fileExtension);

            StorageTask<TaskSnapshot> storageTask = filePart.putFile(imgUri);

            storageTask.continueWithTask(task -> {
                                if (!task.isSuccessful()) {
                                    throw Objects.requireNonNull(task.getException());
                                }
                                return filePart.getDownloadUrl();
                            })
                    .addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                        Uri downloadURL = task.getResult();
                        String imageUrl = downloadURL.toString();

                        DatabaseReference refPost = getRealTimeDatabase().getReference("Posts");

                        String postId = refPost.push().getKey();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("postId", postId);
                        map.put("imageUrl", imageUrl);
                        map.put("description", description.getText().toString());
                        map.put("publisher", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

                        refPost.child(postId).setValue(map);

                        DatabaseReference mHashTagRef = getRealTimeDatabase()
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
                        onStorageServiceListener.onSuccess("");
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            onStorageServiceListener.onFailure(e.getMessage());
                        }
                    });
        }
    }
}
