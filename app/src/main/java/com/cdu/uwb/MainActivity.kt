package com.cdu.uwb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.cdu.uwb.activity.MapRouteVewActivity
import com.cdu.uwb.testclass.GyroScopeClass
import com.cdu.uwb.ui.MapRouteView

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
        //启动陀螺仪测试activity
//        val gyroscope = findViewById<Button>(R.id.start_gyroscope)
//        gyroscope.setOnClickListener {
//            val intent = Intent(this, GyroScopeClass::class.java)
//            startActivity(intent)
//        }



        //这个监听器没任何意义，只是拿来测试用法
//        val mapRouteView = findViewById<MapRouteView>(R.id.MapRouteView)
//        mapRouteView.setOnRedrawFinishedListener(object: MapRouteView.OnRedrawFinishedListener{
//            override fun onRedrawFinished() {
//                Log.d(TAG, "Redraw Finished")
//            }
//
//        })
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