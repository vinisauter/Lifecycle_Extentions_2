package com.example.lifecycleextentions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ReferenceFunction
import androidx.lifecycle.SingleLiveData

class MainActivity : AppCompatActivity() {

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
}
