package com.cdu.uwb.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cdu.uwb.R

class ViewSecond : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_2)
        supportActionBar?.hide()
    }
}