package com.shakeit.core

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

private const val SHAKE_THRESHOLD_GRAVITY = 2.7f
private const val SHAKE_SLOP_TIME_MS = 500 // to take care of very close multiple shake events

object ShakeDetector : SensorEventListener {
    private var mShakeTimestamp: Long = 0
    private val mShakeListeners = mutableListOf<ShakeListener>()

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now()) {
                return
            }
            mShakeTimestamp = now()

            mShakeListeners.forEach({
                it.onShake()
            })
        }
    }

    fun registerForShakeEvent(shakeListener: ShakeListener) {
        if(!mShakeListeners.contains(shakeListener)) {
            mShakeListeners.add(shakeListener)
        }
    }

    fun unRegisterForShakeEvent(shakeListener: ShakeListener) {
        if (mShakeListeners.contains(shakeListener)) {
            mShakeListeners.remove(shakeListener)
        }
    }

    private fun now(): Long {
        return System.currentTimeMillis()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }
}