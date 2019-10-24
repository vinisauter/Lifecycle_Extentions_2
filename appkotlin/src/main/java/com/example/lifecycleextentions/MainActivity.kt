package com.example.lifecycleextentions

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.CompatActivity
import androidx.activity.OnActivityResultCallback
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ReferenceFunction
import androidx.lifecycle.SingleLiveData

class MainActivity : CompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val longLiveData = MutableLiveData<Long>()
        longLiveData.single().observe(this,
            Observer {

            }
        )
        longLiveData.single(ReferenceFunction<Long, SingleLiveData<Boolean>> { input, output ->
            output.setValue(
                input > 0
            )
        }).observe(this,
            Observer {

            }
        )
        longLiveData.observeOnce(this,
            Observer {

            }
        )
    }

    internal fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent,
            object : OnActivityResultCallback() {
                override fun handleOnActivityResult(resultCode: Int, data: Intent?) {

                }
            }
        )
    }
}
