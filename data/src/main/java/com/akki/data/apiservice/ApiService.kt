package com.akki.data.apiservice

import com.akki.domain.enitity.SessionSubmitResult
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/submit-session")
    fun postData(
        @Field("location_id") location_id: String,
        @Field("time_spent") time_spent: Long,
        @Field("end_time") end_time: Float,
    ): Single<SessionSubmitResult>
}