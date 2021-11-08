package com.cdu.uwb.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ObjectAnimator.ofFloat
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.cdu.uwb.R
import com.cdu.uwb.ui.MapRouteView

class MapRouteVewActivity: AppCompatActivity() {

    private lateinit var mMapRouteView: MapRouteView
    private lateinit var mArrow: ImageView
    private lateinit var mMapRoutelayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_route_layout)
        supportActionBar?.hide()
        MovingArrow()
    }

    private fun MovingArrow() {
        //点击屏幕就可以让图片从当前位置移动到目标位置
        mArrow = findViewById(R.id.navigation_arrow)
        mMapRouteView = findViewById(R.id.maprouteview)
        mMapRoutelayout = findViewById(R.id.maproutelayout)

        //有一种思想，使用valueanimator让imageview从当前位置移动到对应的位置，使用imageview.setX和imageview.setY方法，
        // 只要足够频繁就能形成动画效果，objectanimator也是valueanimator形成的
        mMapRoutelayout.setOnClickListener {

        }
    }

}