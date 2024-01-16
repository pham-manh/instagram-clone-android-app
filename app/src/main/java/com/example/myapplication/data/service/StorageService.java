package com.example.myapplication.data.service;

public class StorageService {
//    DatabaseReference mRef = FirebaseDatabase
//                        .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                        .getReference();
//    StorageReference filePart = FirebaseStorage
//            .getInstance("gs://instagram-clone-784ff.appspot.com")
//            .getReference();


//    private String getFileExtension(Uri imageUri) {
//        return MimeTypeMap.getSingleton()
//                .getExtensionFromMimeType(
//                        this.getContentResolver().getType(imageUri)
//                );
//    }
//
//    private void uploadPost(Uri imageUri) {
//        if (imageUri != null) {
//            Log.i("Post", "Uploading");
//            StorageReference filePart = FirebaseStorage.getInstance("gs://instagram-clone-784ff.appspot.com")
//                    .getReference("Posts")
//                    .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
//            StorageTask<TaskSnapshot> storageTask = filePart.putFile(imageUri);
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

}
