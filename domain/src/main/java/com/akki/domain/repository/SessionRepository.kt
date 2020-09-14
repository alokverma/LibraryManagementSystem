package com.akki.domain.repository

interface SessionRepository {
    // fun postSession(data: String): Single<String>
    fun startSession(data: String)
    fun endSession(data: String)
}