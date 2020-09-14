package com.akki.domain.repository

import io.reactivex.Observable
import io.reactivex.Single

interface SessionRepository {
    // fun postSession(data: String): Single<String>
    fun startSession(data: String)
    fun endSession(data: String)

    fun getStartSessionTime(): Long

    fun getEndSessionTime(): Long

    fun checkIsSessionIsValid(): Observable<String>
}