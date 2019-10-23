package com.example.appjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ReferenceFunction;
import androidx.lifecycle.SingleLiveData;

import com.example.lifecycleextentions.LiveDataUtilsKt;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

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
}
