package com.cdu.uwb.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cdu.uwb.R

class ViewFirst : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_1)
        supportActionBar?.hide()
    }
}