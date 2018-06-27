package com.shakeit.core

import android.hardware.SensorEvent
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class ShakeDetectorTest {

    private val shakeListener = mock<ShakeListener>()
    private val shakeListener2 = mock<ShakeListener>()
    private val sensorEvent = mock<SensorEvent>()
    private val valuesField = SensorEvent::class.java.getField("values")

    @Before
    fun setUp() {
        valuesField.isAccessible = true
        ShakeDetector.registerForShakeEvent(shakeListener)
        ShakeDetector.registerForShakeEvent(shakeListener2)
        Thread.sleep(500)
    }

    @Test
    fun shouldTriggerShakeEventOnShake() {
        valuesField.set(sensorEvent, floatArrayOf(30F, 30F, 30F))
        ShakeDetector.onSensorChanged(sensorEvent)
        verify(shakeListener, times(1)).onShake()
        verify(shakeListener2, times(1)).onShake()
    }

    @Test
    fun shouldNotTriggerMultipleShakeOnBackToBackShake() {
        valuesField.set(sensorEvent, floatArrayOf(30F, 30F, 30F))
        ShakeDetector.onSensorChanged(sensorEvent)
        ShakeDetector.onSensorChanged(sensorEvent)
        ShakeDetector.onSensorChanged(sensorEvent)
        verify(shakeListener, times(1)).onShake()
        verify(shakeListener2, times(1)).onShake()
    }

    @Test
    fun shouldNotTriggerShakeEventOnNonShakeEvent() {
        valuesField.set(sensorEvent, floatArrayOf(10F, 10F, 10F))
        ShakeDetector.onSensorChanged(sensorEvent)
        verify(shakeListener, never()).onShake()
        verify(shakeListener2, never()).onShake()
    }

    @Test
    fun shouldNotTriggerShakeEventWhenUnregistered() {
        valuesField.set(sensorEvent, floatArrayOf(30F, 30F, 30F))
        ShakeDetector.unRegisterForShakeEvent(shakeListener)
        ShakeDetector.onSensorChanged(sensorEvent)
        verify(shakeListener, never()).onShake()
        verify(shakeListener2, times(1)).onShake()
    }

    @After
    fun tearDown() {
        valuesField.isAccessible = false
        ShakeDetector.unRegisterForShakeEvent(shakeListener)
        ShakeDetector.unRegisterForShakeEvent(shakeListener2)
    }
}