package com.akki.domain.repository

import io.reactivex.Single

interface SessionRepository {
    fun postSession(data: String): Single<String>
}