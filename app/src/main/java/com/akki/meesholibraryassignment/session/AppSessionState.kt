package com.akki.meesholibraryassignment.session

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.akki.domain.base.SessionKeys
import com.akki.domain.base.SessionState
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
                    value = "End Session"
                } else if (state == SessionState.PAYMENT_SESSION.ordinal) {
                    value = "Pay"
                } else {
                    value = "Scan QRCode"
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
            value = "End Session"
        } else if (state == SessionState.PAYMENT_SESSION.ordinal) {
            value = "Pay"
        } else {
            value = "Scan QRCode"
        }
        sessionCache.registerOnSharedPreferenceChangeListener(mSessionDataListener)
    }

    override fun onInactive() {
        super.onInactive()
        sessionCache.unregisterOnSharedPreferenceChangeListener(mSessionDataListener)

    }


}
