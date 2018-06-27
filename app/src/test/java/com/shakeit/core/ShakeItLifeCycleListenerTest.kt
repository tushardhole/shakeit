package com.shakeit.core

import android.app.Activity
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test

class ShakeItLifeCycleListenerTest {
    private val mockSensorManager = mock<SensorManager>()
    private val mockSensor = mock<Sensor>()
    private val shakeItLifeCycleListener = ShakeItLifeCycleListener(mockSensorManager)
    private val activity = mock<Activity>(extraInterfaces = arrayOf(ShakeListener::class))
    private val sensorEvent = mock<SensorEvent>()
    private val valuesField = SensorEvent::class.java.getField("values")!!


    @Before
    fun setUp() {
        whenever(mockSensorManager.getDefaultSensor(TYPE_ACCELEROMETER)).thenReturn(mockSensor)
        valuesField.isAccessible = true
        Thread.sleep(500) //to ensure every shake is detected and not ignored as duplicate shake
    }

    @Test
    fun shouldRegisterForShakeWhenActivityIsResumed() {
        shakeItLifeCycleListener.onActivityResumed(mock())
        verify(mockSensorManager).registerListener(ShakeDetector, mockSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    @Test
    fun shouldUnRegisterForShakeWhenActivityIsPaused() {
        shakeItLifeCycleListener.onActivityPaused(mock())
        verify(mockSensorManager).unregisterListener(ShakeDetector, mockSensor)
    }

    @Test
    fun shouldRegisterCurrentActivityForShakeWhenActivityIsShakeListenerInOnResume() {
        shakeItLifeCycleListener.onActivityResumed(activity)

        valuesField.set(sensorEvent, floatArrayOf(30F, 30F, 30F))
        ShakeDetector.onSensorChanged(sensorEvent)
        verify(activity as ShakeListener).onShake()
    }

    @Test
    fun shouldUnRegisterCurrentActivityForShakeWhenActivityIsShakeListenerInOnPause() {
        shakeItLifeCycleListener.onActivityResumed(activity)
        shakeItLifeCycleListener.onActivityPaused(activity)

        valuesField.set(sensorEvent, floatArrayOf(30F, 30F, 30F))
        ShakeDetector.onSensorChanged(sensorEvent)
        verify(activity as ShakeListener, never()).onShake()
    }

    @After
    fun tearDown() {
        valuesField.isAccessible = false
    }
}