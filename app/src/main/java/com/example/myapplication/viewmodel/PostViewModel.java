package com.example.myapplication.viewmodel;

import android.net.Uri;
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

public class PostViewModel extends BaseObservable {
    private static final int IMAGE_VIEW_SRC_DEFAULT = R.mipmap.ic_launcher;
    public MutableLiveData<Activity> activityMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Uri> imageUri = new MutableLiveData<>();
    private final ActivityResultLauncher<PickVisualMediaRequest> pickVisualMedia;

    public PostViewModel(ActivityResultLauncher<PickVisualMediaRequest> pickMedia) {
        pickVisualMedia = pickMedia;
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
}
