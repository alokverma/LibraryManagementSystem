package com.akki.meesholibraryassignment.session

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.akki.domain.base.SessionKeys.QR_CODE
import com.akki.domain.enitity.ScanResult
import com.google.gson.Gson
import com.google.gson.JsonParser
import javax.inject.Inject

class AppSessionLiveData @Inject constructor(
    private val sessionCache: SharedPreferences,
    private val gson: Gson,
    private val parser: JsonParser
) :
    LiveData<ScanResult>() {

    private val mSessionDataListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences?, key: String? ->
            if (key == QR_CODE) {
                value = parseString(sessionCache.getString(QR_CODE, ""))
            }
        }

    private fun parseString(data: String?): ScanResult? {
        try {
            val scanResult = gson.fromJson(parser.parse(data).asString, ScanResult::class.java)
            return scanResult
        } catch (e: UnsupportedOperationException) {
            e.printStackTrace()
        }
        return null
    }


    override fun onActive() {
        super.onActive()
        value = parseString(sessionCache.getString(QR_CODE, ""))
        sessionCache.registerOnSharedPreferenceChangeListener(mSessionDataListener)
    }

    override fun onInactive() {
        super.onInactive()
        sessionCache.unregisterOnSharedPreferenceChangeListener(mSessionDataListener)

    }


}