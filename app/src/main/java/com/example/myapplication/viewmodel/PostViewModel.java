package com.example.myapplication.viewmodel;

import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.data.service.MyOnCompleteListener;
import com.example.myapplication.data.service.StorageService;
import com.example.myapplication.utils.Activity;
import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;

public class PostViewModel extends BaseObservable {
    public static final int IMAGE_VIEW_SRC_DEFAULT = R.mipmap.ic_launcher;

    private SocialAutoCompleteTextView description;
    private final ContentResolver contentResolver;
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    public MutableLiveData<Activity> activityMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Uri> imageUri = new MutableLiveData<>();
    private final ActivityResultLauncher<PickVisualMediaRequest> pickVisualMedia;

    public PostViewModel(ContentResolver contentResolver, ActivityResultLauncher<PickVisualMediaRequest> pickMedia) {
        this.contentResolver = contentResolver;
        pickVisualMedia = pickMedia;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    private void showToast(String message) {
        toastMessage.setValue(message);
    }

    public void setDescription(SocialAutoCompleteTextView description) {
        this.description = description;
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

    private String getFileExtension(Uri imageUri) {
        return MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(
                        contentResolver.getType(imageUri)
                );
    }

    public void uploadPost() {
        Uri imgUri = this.getImageUri().getValue();
        String fileExtension = getFileExtension( this.getImageUri().getValue());
        StorageService storageService = new StorageService();
        storageService.uploadPost(imgUri, description, fileExtension, new MyOnCompleteListener() {
            @Override
            public void onSuccess(String successMessage) {
                activityMutableLiveData.setValue(Activity.MAIN_ACTIVITY);
            }

            @Override
            public void onFailure(String errorMessage) {
                showToast(errorMessage);
            }
        });
    }

    public void closePostActivity() {
        activityMutableLiveData.setValue(Activity.MAIN_ACTIVITY);
    }
}
