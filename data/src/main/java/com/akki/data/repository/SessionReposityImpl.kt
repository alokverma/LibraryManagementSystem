package com.akki.data.repository

import com.akki.domain.enitity.ScanResult
import com.akki.domain.repository.SessionRepository
import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.Single
import javax.inject.Inject

class SessionReposityImpl @Inject constructor(
    private val gson: Gson,
    private val parser: JsonParser
) :
    SessionRepository {

//    override fun postSession(data: String): Single<String> {
//        TODO("Not yet implemented")
//    }

    override fun startSession(data: String): Single<ScanResult> {
        return Single.just(data).map { return@map parser.parse(it).asString }
            .map { return@map gson.fromJson(it, ScanResult::class.java) }
    }

//    override fun endSession(): Single<String> {
//        TODO("Not yet implemented")
//    }
}