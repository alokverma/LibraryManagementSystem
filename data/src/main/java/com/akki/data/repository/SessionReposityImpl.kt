package com.akki.data.repository

import android.content.SharedPreferences
import com.akki.data.apiservice.ApiService
import com.akki.data.utility.SchedulersFacade
import com.akki.domain.session.SessionKeys
import com.akki.domain.session.SessionState
import com.akki.domain.base.Utility
import com.akki.domain.enitity.ScanResult
import com.akki.domain.enitity.SessionSubmitResult
import com.akki.domain.repository.SessionRepository
import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import java.util.*
import javax.inject.Inject


class SessionReposityImpl @Inject constructor(
    private val gson: Gson,
    private val parser: JsonParser,
    private val sessionPref: SharedPreferences,
    private val apiService: ApiService,
    private val schedulersFacade: SchedulersFacade
) :
    SessionRepository {

    override fun postSession(data: ScanResult): Single<SessionSubmitResult> {
        return apiService.postData(data.locationId!!, data.totalMin, data.totalPrice)
            .subscribeOn(schedulersFacade.io()).observeOn(schedulersFacade.ui())
    }


    var isValid = PublishSubject.create<Boolean>()

    override fun startSession(data: String) {
        val date = Date(System.currentTimeMillis())
        val millis: Long = date.getTime()
        if (sessionPref.getString(SessionKeys.QR_CODE, "").equals("")) {
            sessionPref.edit().putInt(SessionKeys.SESSION_STATE, SessionState.STARTED.ordinal)
                .apply()
            sessionPref.edit().putLong(SessionKeys.START_TIME, millis).apply()
            sessionPref.edit().putString(SessionKeys.QR_CODE, data).apply()
        } else {
            val newScanResult = Utility.parseJSONtoScanResult(data, gson, parser)
            val prevScanResult =
                Utility.parseJSONtoScanResult(
                    sessionPref.getString(SessionKeys.QR_CODE, ""),
                    gson,
                    parser
                )
            if (newScanResult?.locationId == prevScanResult?.locationId) {
                sessionPref.edit().putInt(
                    SessionKeys.SESSION_STATE,
                    SessionState.PAYMENT_SESSION.ordinal
                )
                    .apply()
                sessionPref.edit().putLong(SessionKeys.END_TIME, millis).apply()
                sessionPref.edit().putString(SessionKeys.QR_CODE, data).apply()
            } else {
                //show user popup to scan correct bar code
                isValid.onNext(true)
               // isValid.onComplete()
            }
        }
    }

    override fun endSession(data: String) {
        sessionPref.edit().putString(SessionKeys.QR_CODE, "").apply()
        sessionPref.edit().putInt(SessionKeys.SESSION_STATE, SessionState.END_SESSION.ordinal)
            .apply()
        sessionPref.edit().clear().apply()
    }

    override fun getStartSessionTime(): Long {
        return sessionPref.getLong(SessionKeys.START_TIME, 0L)
    }


    override fun getEndSessionTime(): Long {
        return sessionPref.getLong(SessionKeys.END_TIME, 0L)
    }

    override fun checkIsSessionIsValid(): Observable<Boolean> =
        isValid.subscribeOn(schedulersFacade.ui()).observeOn(schedulersFacade.ui())


}