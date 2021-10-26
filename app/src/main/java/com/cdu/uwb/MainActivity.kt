package com.cdu.uwb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cdu.uwb.ui.MapRouteView

class MainActivity : AppCompatActivity(){

    companion object{
        @JvmStatic
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val mapRouteView = findViewById<MapRouteView>(R.id.MapRouteView)
        //这个监听器没任何意义，只是拿来测试用法
//        mapRouteView.setOnRedrawFinishedListener(object: MapRouteView.OnRedrawFinishedListener{
//            override fun onRedrawFinished() {
//                Log.d(TAG, "Redraw Finished")
//            }
//
//        })
    }
}