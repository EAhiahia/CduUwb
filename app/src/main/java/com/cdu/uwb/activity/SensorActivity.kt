package com.cdu.uwb.activity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent

import android.hardware.SensorManager

import android.widget.TextView

import android.os.Bundle

import android.hardware.SensorEventListener
import android.view.View
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import com.cdu.uwb.R
import java.lang.StringBuilder


class SensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var mSensorManager: SensorManager
//    private lateinit var mTxtValue1: TextView
    private lateinit var mTxtValue2: TextView
//    private lateinit var mTxtValue3: TextView
//    private lateinit var mTxtValue4: TextView
//    private lateinit var mTxtValue5: TextView
//    private lateinit var mTxtValue6: TextView
//    private lateinit var mTxtValue7: TextView
//    private lateinit var mTxtValue8: TextView
//    private lateinit var mTxtValue9: TextView
    private lateinit var mArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sensor_layout)
//        mTxtValue1 = findViewById(R.id.textView)
        mTxtValue2 = findViewById(R.id.textView2)
//        mTxtValue3 = findViewById(R.id.textView3)
//        mTxtValue4 = findViewById(R.id.textView4)
//        mTxtValue5 = findViewById(R.id.textView5)
//        mTxtValue6 = findViewById(R.id.textView6)
//        mTxtValue7 = findViewById(R.id.textView7)
//        mTxtValue8 = findViewById(R.id.textView8)
//        mTxtValue9 = findViewById(R.id.textView9)
        mArrow = findViewById(R.id.arrow)

        // 获取传感器管理对象
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
//        // 为加速度传感器注册监听器
//        mSensorManager.registerListener(
//            this,
//            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//            SensorManager.SENSOR_DELAY_GAME
//        )
        // 为方向传感器注册监听器
        mSensorManager.registerListener(
            this,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
//        // 为陀螺仪传感器注册监听器
//        mSensorManager.registerListener(
//            this,
//            mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
//            SensorManager.SENSOR_DELAY_GAME
//        )
//        // 为磁场传感器注册监听器
//        mSensorManager.registerListener(
//            this,
//            mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
//            SensorManager.SENSOR_DELAY_GAME
//        )
//        // 为重力传感器注册监听器
//        mSensorManager.registerListener(
//            this,
//            mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
//            SensorManager.SENSOR_DELAY_GAME
//        )
//        // 为线性加速度传感器注册监听器
//        mSensorManager.registerListener(
//            this,
//            mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
//            SensorManager.SENSOR_DELAY_GAME
//        )
//        // 为温度传感器注册监听器
//        mSensorManager.registerListener(
//            this,
//            mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
//            SensorManager.SENSOR_DELAY_GAME
//        )
//        // 为光传感器注册监听器
//        mSensorManager.registerListener(
//            this,
//            mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
//            SensorManager.SENSOR_DELAY_GAME
//        )
//        // 为压力传感器注册监听器
//        mSensorManager.registerListener(
//            this,
//            mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
//            SensorManager.SENSOR_DELAY_GAME
//        )
    }

    override fun onStop() {
        super.onStop()
        // 取消监听
        mSensorManager.unregisterListener(this)
    }

    // 当传感器的值改变的时候回调该方法
    override fun onSensorChanged(event: SensorEvent) {
        val values = event.values
        // 获取传感器类型
        val type = event.sensor.type
        val sb: StringBuilder
        when (type) {
//            Sensor.TYPE_ACCELEROMETER -> {
//                sb = StringBuilder()
//                sb.append("加速度传感器返回数据：")
//                sb.append("\nX方向的加速度：")
//                sb.append(values[0])
//                sb.append("\nY方向的加速度：")
//                sb.append(values[1])
//                sb.append("\nZ方向的加速度：")
//                sb.append(values[2])
//                mTxtValue1.text = sb.toString()
//            }
            Sensor.TYPE_ORIENTATION -> {
                sb = StringBuilder()
                sb.append("\n方向传感器返回数据：")
                sb.append("\n绕Z轴转过的角度：")
                sb.append(values[0])
                sb.append("\n绕X轴转过的角度：")
                sb.append(values[1])
                sb.append("\n绕Y轴转过的角度：")
                sb.append(values[2])
                mTxtValue2.text = sb.toString()
                mArrow.rotation = values[0]
            }
//            Sensor.TYPE_GYROSCOPE -> {
//                sb = StringBuilder()
//                sb.append("\n陀螺仪传感器返回数据：")
//                sb.append("\n绕X轴旋转的角速度：")
//                sb.append(values[0])
//                sb.append("\n绕Y轴旋转的角速度：")
//                sb.append(values[1])
//                sb.append("\n绕Z轴旋转的角速度：")
//                sb.append(values[2])
//                mTxtValue3.text = sb.toString()
//            }
//            Sensor.TYPE_MAGNETIC_FIELD -> {
//                sb = StringBuilder()
//                sb.append("\n磁场传感器返回数据：")
//                sb.append("\nX轴方向上的磁场强度：")
//                sb.append(values[0])
//                sb.append("\nY轴方向上的磁场强度：")
//                sb.append(values[1])
//                sb.append("\nZ轴方向上的磁场强度：")
//                sb.append(values[2])
//                mTxtValue4.text = sb.toString()
//            }
//            Sensor.TYPE_GRAVITY -> {
//                sb = StringBuilder()
//                sb.append("\n重力传感器返回数据：")
//                sb.append("\nX轴方向上的重力：")
//                sb.append(values[0])
//                sb.append("\nY轴方向上的重力：")
//                sb.append(values[1])
//                sb.append("\nZ轴方向上的重力：")
//                sb.append(values[2])
//                mTxtValue5.text = sb.toString()
//            }
//            Sensor.TYPE_LINEAR_ACCELERATION -> {
//                sb = StringBuilder()
//                sb.append("\n线性加速度传感器返回数据：")
//                sb.append("\nX轴方向上的线性加速度：")
//                sb.append(values[0])
//                sb.append("\nY轴方向上的线性加速度：")
//                sb.append(values[1])
//                sb.append("\nZ轴方向上的线性加速度：")
//                sb.append(values[2])
//                mTxtValue6.text = sb.toString()
//            }
//            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
//                sb = StringBuilder()
//                sb.append("\n温度传感器返回数据：")
//                sb.append("\n当前温度为：")
//                sb.append(values[0])
//                mTxtValue7.text = sb.toString()
//            }
//            Sensor.TYPE_LIGHT -> {
//                sb = StringBuilder()
//                sb.append("\n光传感器返回数据：")
//                sb.append("\n当前光的强度为：")
//                sb.append(values[0])
//                mTxtValue8.text = sb.toString()
//            }
//            Sensor.TYPE_PRESSURE -> {
//                sb = StringBuilder()
//                sb.append("\n压力传感器返回数据：")
//                sb.append("\n当前压力为：")
//                sb.append(values[0])
//                mTxtValue9.text = sb.toString()
//            }
        }
    }

    // 当传感器精度发生改变时回调该方法
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}