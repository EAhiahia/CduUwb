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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_route_layout)
        supportActionBar?.hide()
    }

}