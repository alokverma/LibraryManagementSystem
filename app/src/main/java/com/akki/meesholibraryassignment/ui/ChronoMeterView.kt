package com.akki.meesholibraryassignment.ui

import android.content.SharedPreferences
import android.os.SystemClock
import android.widget.Chronometer
import com.akki.domain.base.SessionKeys.KEY_BASE
import com.akki.domain.base.SessionKeys.KEY_STATE
import com.akki.domain.base.SessionKeys.KEY_TIME_PAUSED

class ChronoMeterView private constructor(private val sessionPref: SharedPreferences) {

    lateinit var mChronometer: Chronometer
    private var mTimeWhenPaused: Long = 0
    private var mTimeBase: Long = 0

    val isRunning: Boolean
        get() = ChronoMeterState.values()[sessionPref.getInt(
            KEY_STATE,
            ChronoMeterState.Stopped.ordinal
        )] == ChronoMeterState.Running

    val isPaused: Boolean
        get() = ChronoMeterState.values()[sessionPref.getInt(
            KEY_STATE,
            ChronoMeterState.Stopped.ordinal
        )] == ChronoMeterState.Paused

    fun pauseChronometer() {
        storeState(ChronoMeterState.Paused)
        saveTimeWhenPaused()
        pauseStateChronometer()
    }

    private fun saveTimeWhenPaused() {
        sessionPref.edit()
            .putLong(
                KEY_TIME_PAUSED,
                mChronometer.base - SystemClock.elapsedRealtime()
            )
            .apply()
    }

    fun startChronometer() {
        storeState(ChronoMeterState.Running)
        saveBase()
        startStateChronometer()
    }

    fun setInHourFormat() {
        mChronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener { c ->
            val elapsedMillis = SystemClock.elapsedRealtime() - c.base
            if (elapsedMillis > 3600000L) {
                c.format = "0%s"

            } else {
                c.format = "00:%s"
            }
        }
    }

    private fun storeState(state: ChronoMeterState) {
        sessionPref.edit().putInt(KEY_STATE, state.ordinal).apply()
    }

    private fun saveBase() {
        sessionPref.edit()
            .putLong(KEY_BASE, SystemClock.elapsedRealtime())
            .apply()
    }

    private fun clearState() {
        storeState(ChronoMeterState.Stopped)
        sessionPref.edit()
            .remove(KEY_BASE)
            .remove(KEY_TIME_PAUSED)
            .apply()
        mTimeWhenPaused = 0
    }


    private fun startStateChronometer() {
        mTimeBase = sessionPref.getLong(
            KEY_BASE,
            0
        )
        mTimeWhenPaused = sessionPref.getLong(KEY_TIME_PAUSED, 0)
        mChronometer.base = mTimeBase + mTimeWhenPaused
        mChronometer.start()
    }

    fun resumeState() {
        val state = ChronoMeterState.values()[sessionPref.getInt(
            KEY_STATE,
            ChronoMeterState.Stopped.ordinal
        )]
        if (state.ordinal == ChronoMeterState.Stopped.ordinal) {
            stopChronometer()
        } else if (state.ordinal == ChronoMeterState.Paused.ordinal) {
            pauseStateChronometer()
        } else {
            startStateChronometer()
        }
    }

    fun stopChronometer() {
        storeState(ChronoMeterState.Stopped)
        mChronometer.base = SystemClock.elapsedRealtime()
        mChronometer.stop()
        mChronometer.text = "00:00:00"
        clearState()
    }

    private fun pauseStateChronometer() {
        mTimeWhenPaused = sessionPref.getLong(
            KEY_TIME_PAUSED,
            mChronometer.base - SystemClock.elapsedRealtime()
        )
        //some negative value
        mChronometer.base = SystemClock.elapsedRealtime() + mTimeWhenPaused
        mChronometer.stop()
        val text = mChronometer.text
        if (text.length == 5) {
            mChronometer.text = "00:$text"
        } else if (text.length == 7) {
            mChronometer.text = "0$text"
        }
    }

    companion object {

        fun getInstance(
            chronometer: Chronometer,
            sharedPreferences: SharedPreferences
        ): ChronoMeterView {
            val chronometerPersist = ChronoMeterView(sharedPreferences)

            chronometerPersist.mChronometer = chronometer
            return chronometerPersist

        }
    }

}