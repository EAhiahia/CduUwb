package com.cdu.uwb

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.cdu.uwb.activity.*
import com.cdu.uwb.data.Position
import com.cdu.uwb.ui.MapRouteView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.StringBuilder
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), View.OnClickListener, SensorEventListener {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var sosButton: ImageView
    private lateinit var mapRouteView: MapRouteView
    private lateinit var arrowImage: ImageView
    private lateinit var speedText: TextView
    private lateinit var distanceText: TextView
    private lateinit var stateImage: ImageView

    private lateinit var mSensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        sosButton = findViewById(R.id.sos_button)
        mapRouteView = findViewById(R.id.maprouteview)
        arrowImage = findViewById(R.id.arrow_image)
        speedText = findViewById(R.id.speed_text)
        distanceText = findViewById(R.id.distance_text)
        stateImage = findViewById(R.id.state_image)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //给ImageView设置图片，并设置到路线的起点，实际上是设置到用户当前位置
        arrowImage.setImageResource(R.drawable.navigation_arrow_image)
        /**
         * TODO: 这里需要减去图片的一半宽度，但是使用.width获得的是0，因为Imageview实际上并没有加载完，建议直接获取imageview的一半宽度，不使用.width
         */
        arrowImage.x = mapRouteView.getPosition()[0].x
        arrowImage.y = mapRouteView.getPosition()[0].y

        sosButton.setOnClickListener(this)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        //默认选中这个界面
        navView.setCheckedItem(R.id.home_page)
        //点击一个就自动关闭，侧滑栏的按钮
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_page -> {
                    Toast.makeText(this, "You clicked 首页.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewFirst::class.java)
                    startActivity(intent)
                }
                R.id.instructions -> {
                    Toast.makeText(this, "You clicked 软件使用说明.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewFirst::class.java)
                    startActivity(intent)
                }
                R.id.map_all -> {
                    Toast.makeText(this, "You clicked 场地地图查看.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewSecond::class.java)
                    startActivity(intent)
                }
                R.id.evacuate_knowledge -> {
                    Toast.makeText(this, "You clicked 应急疏散知识.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewThird::class.java)
                    startActivity(intent)
                }
                R.id.first_aid_knowledge -> {
                    Toast.makeText(this, "You clicked 常见急救方法.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewFour::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(
            this,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onStop() {
        super.onStop()
        // 取消监听
        mSensorManager.unregisterListener(this)
    }

    /**
     * 箭头随人位置而移动的动画
     * TODO: 循环获取mapRouteView.mPosition中的测试数据，并在启动过后更改sos_button的按钮功能为结束及图片为停止
     * TODO: 以后改为后台循环获取uwb定位并做动画
     */
    private fun doAnimator() {
        Thread {
            for (i in mapRouteView.getPosition()) {
                runOnUiThread {
                    /**
                     * 保存图片的原始坐标
                     */
                    var originX = arrowImage.x
                    var originY = arrowImage.y

                    /**
                     * 内部应该是  目的坐标 - 初始坐标，便是移动的距离
                     */
                    var arrowAnimatorX = ValueAnimator.ofFloat(0f, i.x - originX)
                    arrowAnimatorX.addUpdateListener {
                        var value = it.animatedValue as Float
                        arrowImage.x = originX + value
                        Log.d(TAG, "doAnimator: value -> $value, arrowImage.x -> ${arrowImage.x}")
                    }
                    var arrowAnimatorY = ValueAnimator.ofFloat(0f, i.y - originY)
                    arrowAnimatorY.addUpdateListener {
                        var value = it.animatedValue as Float
                        arrowImage.y = originY + value
                    }
                    var animatorAll = AnimatorSet()
                    animatorAll.playTogether(arrowAnimatorX, arrowAnimatorY)
                    animatorAll.duration = 500
                    animatorAll.start()
                }
                Thread.sleep(1000)
            }
        }.start()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sos_button -> {
                doAnimator()
                changeSpeed()
                changeDistance()
            }
        }
    }

    private fun changeDistance() {
        Thread {
            for (i in mapRouteView.getDistance()) {
                runOnUiThread {
                    distanceText.text = i.distance.toString()
                }
                Log.d(TAG, "changeDistance: ${i.distance}")
                Thread.sleep(1000)
            }
        }.start()
    }

    private fun changeSpeed() {
        Thread {
            for (i in mapRouteView.getSpeed()) {
                runOnUiThread {
                    speedText.text = i.speed.toString()
                    Log.d(TAG, "changeSpeed: ${i.speed}")
                    if (i.speed >= 30) {
                        stateImage.setImageResource(R.drawable.running_image)
                    } else if (i.speed <= 1) {
                        stateImage.setImageResource(R.drawable.standing_image)
                    } else {
                        stateImage.setImageResource(R.drawable.walking_image)
                    }
                }
                Thread.sleep(1000)
            }
        }.start()

    }

    //标题栏的按钮
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }

    override fun onSensorChanged(event: SensorEvent) {
        val values = event.values
        // 获取传感器类型
        val type = event.sensor.type
        val sb: StringBuilder
        when (type) {
            Sensor.TYPE_ORIENTATION -> {
//                sb = StringBuilder()
//                sb.append("\n方向传感器返回数据：")
//                sb.append("\n绕Z轴转过的角度：")
//                sb.append(values[0])
//                sb.append("\n绕X轴转过的角度：")
//                sb.append(values[1])
//                sb.append("\n绕Y轴转过的角度：")
//                sb.append(values[2])
//                mTxtValue2.text = sb.toString()
                arrowImage.rotation = values[0]
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}