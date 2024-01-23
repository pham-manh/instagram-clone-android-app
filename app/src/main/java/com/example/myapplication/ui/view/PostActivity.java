package com.example.myapplication.ui.view;

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
import com.example.myapplication.utils.Activity;
import com.example.myapplication.utils.ActivityUtils;
import com.example.myapplication.ui.viewmodel.PostViewModel;
import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;
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
        SocialAutoCompleteTextView description = findViewById(R.id.description);

        activityPostBinding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        activityPostBinding.setPostViewModel(new PostViewModel(getContentResolver(), pickMedia));

        activityPostBinding.getPostViewModel()
                .setDescription(description);
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
        activityPostBinding.getPostViewModel()
                .getToastMessage()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String message) {
                        showToast(message);
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
                showToast("Fail to upload Image!");
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}