package com.akki.domain.repository

import com.akki.domain.enitity.ScanResult
import io.reactivex.Single

interface SessionRepository {
    // fun postSession(data: String): Single<String>
    fun startSession(data: String): Single<ScanResult>
    //  fun endSession(): Single<String>
}