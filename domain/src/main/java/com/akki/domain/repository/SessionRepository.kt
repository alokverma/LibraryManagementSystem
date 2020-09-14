package com.akki.domain.repository

import com.akki.domain.enitity.ScanResult
import com.akki.domain.enitity.SessionSubmitResult
import io.reactivex.Observable
import io.reactivex.Single

interface SessionRepository {

    fun postSession(scanResult: ScanResult): Single<SessionSubmitResult>

    fun startSession(data: String)

    fun endSession(data: String)

    fun getStartSessionTime(): Long

    fun getEndSessionTime(): Long

    fun checkIsSessionIsValid(): Observable<String>
}