package com.akki.data.repository

import com.akki.domain.enitity.ScanResult
import com.akki.domain.repository.SessionRepository
import com.google.gson.Gson
import io.reactivex.Single
import javax.inject.Inject

class SessionReposityImpl @Inject constructor() : SessionRepository {

//    override fun postSession(data: String): Single<String> {
//        TODO("Not yet implemented")
//    }

    override fun startSession(data: String): Single<ScanResult> {
        val gson = Gson()
        return Single.just(data).map {
            return@map gson.fromJson(it, ScanResult::class.java)
        }
    }

//    override fun endSession(): Single<String> {
//        TODO("Not yet implemented")
//    }
}