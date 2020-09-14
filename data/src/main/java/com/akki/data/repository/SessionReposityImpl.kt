package com.akki.data.repository

import android.content.SharedPreferences
import com.akki.domain.base.SessionKeys
import com.akki.domain.base.SessionState
import com.akki.domain.repository.SessionRepository
import com.google.gson.Gson
import com.google.gson.JsonParser
import javax.inject.Inject

class SessionReposityImpl @Inject constructor(
    private val gson: Gson,
    private val parser: JsonParser,
    private val sessionPref: SharedPreferences
) :
    SessionRepository {

//    override fun postSession(data: String): Single<String> {
//        TODO("Not yet implemented")
//    }

    override fun startSession(data: String) {
        // 1- if pref is empty then store the data in pref and update the ui
        // 2- if pref is not empty then check this data with new data and if data matches
        // show it to ui that data is not matching a
        // 3- if data is matching then clear the session and update the ui and stop timer
        //
        if (sessionPref.getString(SessionKeys.QR_CODE, "").equals("")) {
            sessionPref.edit().putInt(SessionKeys.SESSION_STATE, SessionState.STARTED.ordinal)
                .apply()
        } else {
            // match data with equality if they are equal then save it
            sessionPref.edit().putInt(SessionKeys.SESSION_STATE, SessionState.PAYMENT_SESSION.ordinal)
                .apply()
        }
        sessionPref.edit().putString(SessionKeys.QR_CODE, data).apply()

    }

    override fun endSession(data: String) {
        sessionPref.edit().putString(SessionKeys.QR_CODE, "").apply()
        sessionPref.edit().clear().apply()
    }
}