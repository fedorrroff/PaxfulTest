package com.example.paxfultest.screens.base

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector : SensorEventListener {

    private var mShakeTimestamp: Long = 0

    private var mListener: OnShakeListener? = null

    fun setMListener(listener: OnShakeListener) {
        mListener = listener
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //not needed
    }

    override fun onSensorChanged(event: SensorEvent?) {

        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        // gForce will be close to 1 when there is no movement.
        val gForce: Float = sqrt(gX * gX + gY * gY + gZ * gZ)

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            // ignore shake events too close to each other (500ms)
            if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                return
            }

            mShakeTimestamp = now
            mListener?.onShake()
        }
    }

    interface OnShakeListener {
        fun onShake()
    }

    companion object {
        const val SHAKE_THRESHOLD_GRAVITY = 2.7f
        const val SHAKE_SLOP_TIME_MS = 500
    }
}