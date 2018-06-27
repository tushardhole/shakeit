package com.shakeit.core

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

class ShakeItTest {

    @Test
    fun shouldRegisterActivityLifeCycleCallBacks() {
        val mockApplication = mock<Application>()
        whenever(mockApplication.getSystemService(Context.SENSOR_SERVICE)).thenReturn(mock<SensorManager>())
        ShakeIt.init(mockApplication)
        verify(mockApplication).registerActivityLifecycleCallbacks(any<ShakeItLifeCycleListener>())
    }
}