package com.cdu.uwb.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import java.util.*

/**
 * 需要在manifest中注册服务
 */
class AskDataService : Service() {

    val askData = 1
    val mTimer = Timer()

    companion object{
        @JvmStatic
        val TAG = "AskDataService"
    }

    //该类中的唯一抽象方法
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    //Service创建时调用
    override fun onCreate() {
        super.onCreate()
    }

    //每次启动Service时调用，第一次启动服务的时候onCreate和onStartCommand都会调用，第二次才只会调用这个
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        autoAskData()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun autoAskData() {
        mTimer.scheduleAtFixedRate(object: TimerTask(){
            override fun run() {
                var message = Message()
                message.what = askData

            }
        }, 1000, 6000)}

    //Service销毁时调用
    override fun onDestroy() {
        super.onDestroy()
    }

    val mHandler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                askData -> Log.d(TAG, "dosomething")
            }
        }
    }
}