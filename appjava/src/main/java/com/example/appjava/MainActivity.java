package com.example.appjava;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.CompatActivity;
import androidx.activity.OnActivityResultCallback;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ReferenceFunction;
import androidx.lifecycle.SingleLiveData;

import com.example.lifecycleextentions.LiveDataUtilsKt;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends CompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LiveData<Long> longLiveData = new MutableLiveData<>();
        LiveDataUtilsKt.single(longLiveData).observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {

            }
        });
        LiveDataUtilsKt.single(longLiveData, new ReferenceFunction<Long, SingleLiveData<Boolean>>() {
            @Override
            public void apply(@NotNull Long input, @NotNull SingleLiveData<Boolean> output) {
                output.setValue(input > 0);
            }
        }).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });
        LiveDataUtilsKt.observeOnce(longLiveData, this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {

            }
        });
    }

    void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,
                new OnActivityResultCallback() {
                    @Override
                    public void handleOnActivityResult(int resultCode, @Nullable Intent data) {

                    }
                }
        );
    }
}
