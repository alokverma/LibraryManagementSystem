package com.akki.domain.repository

import com.akki.domain.enitity.ScanResult
import com.akki.domain.enitity.SessionSubmitResult
import io.reactivex.Single

interface SessionRepository {

    /*
        post the session state to server
     */
    fun postSession(scanResult: ScanResult): Single<SessionSubmitResult>

    /*
        start the session when user clicks start button
     */
    fun startSession(data: String): Single<Int>

    /*
        end session when user clicks on end session
     */
    fun endSession(data: String)

    /*
        return start time of session
     */
    fun getStartSessionTime(): Long

    /*
        return end time of session
     */
    fun getEndSessionTime(): Long

}