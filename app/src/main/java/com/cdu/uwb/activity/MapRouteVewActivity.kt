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
        mNavigationArrow = findViewById(R.id.navigation_arrow)
        mMapRouteView = findViewById(R.id.maprouteview)
        MoveNavigationArrow()
    }

    private fun MoveNavigationArrow() {
//        var location = IntArray(2)
//        mNavigationArrow.getLocationInWindow(location)
//        var mNavigationArrowTranslate =
//            TranslateAnimation(location[0] - 0f, -mMapRouteView.mPositionPoint[0],
//                location[1] - 0f, -mMapRouteView.mPositionPoint[1])
//        mNavigationArrow.animation = mNavigationArrowTranslate
//        mNavigationArrowTranslate.duration = 1000
//        mNavigationArrow.setImageDrawable(resources.getDrawable(R.drawable.navigation_arrow))
//        mNavigationArrowTranslate.start()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            var path = Path().apply {
                lineTo(mMapRouteView.mPositionPoint[0] - 50f, mMapRouteView.mPositionPoint[1] - 50f)
            }
            val animator = ObjectAnimator.ofFloat(mNavigationArrow, View.X,
                View.Y, path).apply {
                duration = 1000
                start()
            }
        } else {
            //Create animator without using line path
        }
    }
}