package com.cdu.uwb.testclass

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cdu.uwb.R

class GyroScopeClass : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscopeSensor: Sensor
    private lateinit var gyroscopeEventListener: SensorEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gyroscope_layout)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        if (gyroscopeSensor==null) {
            Toast.makeText(this, "The device has no Gyroscope !", Toast.LENGTH_SHORT).show()
            finish()
        }

        gyroscopeEventListener = object: SensorEventListener{
            override fun onSensorChanged(event: SensorEvent?) {
                if (event!!.values[2] > 0.5f){
                    window.decorView.setBackgroundColor(Color.BLUE)
                } else if (event.values[2] < -0.5f){
                    window.decorView.setBackgroundColor(Color.YELLOW)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(gyroscopeEventListener)
    }
}