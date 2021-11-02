package com.cdu.uwb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.cdu.uwb.testclass.GyroScopeClass
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
}