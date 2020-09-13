package com.akki.meesholibraryassignment.ui

import android.content.SharedPreferences
import android.os.SystemClock
import android.widget.Chronometer

class ChronoMeterView private constructor(private val myPref: SharedPreferences) {
    lateinit var mChronometer: Chronometer
    private var mTimeWhenPaused: Long = 0
    private var mTimeBase: Long = 0


    fun startChronometer() {
        startStateChronometer()
    }


    private fun startStateChronometer() {
        mChronometer.base = SystemClock.elapsedRealtime()
        mChronometer.start()
    }

    companion object {

        fun getInstance(
            chronometer: Chronometer, identifier: String,
            sharedPreferences: SharedPreferences
        ): ChronoMeterView {
            val chronometerPersist = ChronoMeterView(sharedPreferences)
            chronometerPersist.mChronometer = chronometer
            return chronometerPersist

        }
    }

}