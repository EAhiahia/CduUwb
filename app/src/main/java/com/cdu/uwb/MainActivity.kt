package com.cdu.uwb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.cdu.uwb.activity.MapRouteVewActivity

class MainActivity : AppCompatActivity(), View.OnClickListener{

    companion object{
        @JvmStatic
        val TAG = "MainActivity"
    }

    private lateinit var start_maprouteview: Button
    private lateinit var start_gyroscope: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        start_maprouteview = findViewById(R.id.start_maprouteview)
        start_gyroscope = findViewById(R.id.start_gyroscope)
        start_maprouteview.setOnClickListener(this)
        start_gyroscope.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.start_maprouteview -> {
                Toast.makeText(this, "You clicked start_maprouteview.", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, MapRouteVewActivity::class.java)
                startActivity(intent)
            }
            R.id.start_gyroscope -> {
                Toast.makeText(this, "You clicked start_gyroscope.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}