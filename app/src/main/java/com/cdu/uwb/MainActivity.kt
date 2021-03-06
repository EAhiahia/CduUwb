package com.cdu.uwb

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
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
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), View.OnClickListener {

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

        //???ImageView????????????????????????????????????????????????????????????????????????????????????
        arrowImage.setImageResource(R.drawable.navigation_arrow_image)
        /**
         * TODO: ??????????????????????????????????????????????????????.width????????????0?????????Imageview????????????????????????????????????????????????imageview???????????????????????????.width
         */
        arrowImage.x = mapRouteView.getPosition()[0].x
        arrowImage.y = mapRouteView.getPosition()[0].y

        sosButton.setOnClickListener(this)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        //????????????????????????
        navView.setCheckedItem(R.id.home_page)
        //????????????????????????????????????????????????
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_page -> {
                    Toast.makeText(this, "You clicked ??????.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewFirst::class.java)
                    startActivity(intent)
                }
                R.id.instructions -> {
                    Toast.makeText(this, "You clicked ??????????????????.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewFirst::class.java)
                    startActivity(intent)
                }
                R.id.map_all -> {
                    Toast.makeText(this, "You clicked ??????????????????.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewSecond::class.java)
                    startActivity(intent)
                }
                R.id.evacuate_knowledge -> {
                    Toast.makeText(this, "You clicked ??????????????????.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewThird::class.java)
                    startActivity(intent)
                }
                R.id.first_aid_knowledge -> {
                    Toast.makeText(this, "You clicked ??????????????????.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, ViewFour::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    /**
     * ????????????????????????????????????
     * TODO: ????????????mapRouteView.mPosition?????????????????????????????????????????????sos_button??????????????????????????????????????????
     * TODO: ??????????????????????????????uwb??????????????????
     */
    private fun doAnimator() {
        Thread {
            for (i in mapRouteView.getPosition()) {
                runOnUiThread {
                    /**
                     * ???????????????????????????
                     */
                    var originX = arrowImage.x
                    var originY = arrowImage.y

                    /**
                     * ???????????????  ???????????? - ????????????????????????????????????
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

    //??????????????????
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }
}