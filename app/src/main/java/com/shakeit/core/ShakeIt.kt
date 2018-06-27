package com.shakeit.core

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import com.shakeit.core.ShakeItLifeCycleListener

object ShakeIt {
    fun init(application: Application) {
        val shakeItLifeCycleListener = ShakeItLifeCycleListener(application.getSystemService(Context.SENSOR_SERVICE) as SensorManager)
        application.registerActivityLifecycleCallbacks(shakeItLifeCycleListener)
    }
}