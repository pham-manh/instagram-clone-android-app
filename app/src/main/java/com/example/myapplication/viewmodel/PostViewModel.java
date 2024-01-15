package com.example.myapplication.viewmodel;

import android.net.Uri;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.myapplication.utils.Activity;
public class PostViewModel extends BaseObservable {
    public MutableLiveData<Activity> activityMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Uri> imageUri = new MutableLiveData<>();

    private final ActivityResultLauncher<PickVisualMediaRequest> pickVisualMedia;

    public PostViewModel(ActivityResultLauncher<PickVisualMediaRequest> pickMedia) {
        pickVisualMedia = pickMedia;
    }

    public MutableLiveData<Activity> getActivityMutableLiveData() {
        return activityMutableLiveData;
    }
    public MutableLiveData<Uri> getImageUri() {
        return imageUri;
    }

    @BindingAdapter("android:src")
    public static void setImageURI(ImageView view, MutableLiveData<Uri> liveData) {
        liveData.observe((LifecycleOwner) view.getContext(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                view.setImageURI(uri);
            }
        });
    }

    public void closePostActivity() {
        activityMutableLiveData.setValue(Activity.MAIN_ACTIVITY);
    }

    public void pickImg() {
        pickVisualMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }
}
