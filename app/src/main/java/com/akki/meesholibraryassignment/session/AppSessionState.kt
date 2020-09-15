package com.akki.meesholibraryassignment.session

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.akki.domain.session.SessionButtonState
import com.akki.domain.session.SessionKeys
import com.akki.domain.session.SessionState
import javax.inject.Inject

class AppSessionState @Inject constructor(
    private val sessionCache: SharedPreferences,
) :
    LiveData<String>() {

    private val mSessionDataListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences?, key: String? ->
            if (key == SessionKeys.SESSION_STATE) {
                val state = sessionCache.getInt(
                    SessionKeys.SESSION_STATE,
                    SessionState.END_SESSION.ordinal
                )
                if (state == SessionState.STARTED.ordinal) {
                    value = SessionButtonState.END_SESSION.toString()
                } else if (state == SessionState.PAYMENT_SESSION.ordinal) {
                    value = SessionButtonState.PAY_SESSION.toString()
                } else {
                    value = SessionButtonState.SCAN_QR_CODE.toString()
                }
            }
        }


    override fun onActive() {
        super.onActive()
        val state = sessionCache.getInt(
            SessionKeys.SESSION_STATE,
            SessionState.END_SESSION.ordinal
        )
        if (state == SessionState.STARTED.ordinal) {
            value = SessionButtonState.END_SESSION.toString()
        } else if (state == SessionState.PAYMENT_SESSION.ordinal) {
            value = SessionButtonState.PAY_SESSION.toString()
        } else {
            value = SessionButtonState.SCAN_QR_CODE.toString()
        }
        sessionCache.registerOnSharedPreferenceChangeListener(mSessionDataListener)
    }

    override fun onInactive() {
        super.onInactive()
        sessionCache.unregisterOnSharedPreferenceChangeListener(mSessionDataListener)

    }


}
