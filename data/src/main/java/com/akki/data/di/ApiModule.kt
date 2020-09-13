package com.akki.data.di.module

import com.akki.data.apiservice.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun bindApiService(retrofit: Retrofit): ApiService {
        val apiService = retrofit.create(ApiService::class.java)
        return apiService
    }


}