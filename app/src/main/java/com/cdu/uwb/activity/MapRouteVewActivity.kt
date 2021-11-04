package com.cdu.uwb.activity

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.cdu.uwb.R
import com.cdu.uwb.ui.MapRouteView

class MapRouteVewActivity: AppCompatActivity() {

    private lateinit var mNavigationArrow: ImageView
    private lateinit var mMapRouteView: MapRouteView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_route_layout)
        supportActionBar?.hide()
    }

    //让用户位置的箭头图片随路线移动
    private fun MoveNavigationArrow() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            var path = Path().apply {
                moveTo(mMapRouteView.mPositionPoint[0] - 50f, mMapRouteView.mPositionPoint[1] - 50f)
                lineTo(mMapRouteView.mPositionPoint[0] - 50f, mMapRouteView.mPositionPoint[1] - 50f)
            }
            val animator = ObjectAnimator.ofFloat(mNavigationArrow, View.X,
                View.Y, path).apply {
                duration = 1000
                start()
            }
        }
    }
}