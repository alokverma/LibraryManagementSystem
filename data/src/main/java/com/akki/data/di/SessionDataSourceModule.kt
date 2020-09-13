package com.akki.data.di

import com.akki.data.data.SessionDataSource
import com.akki.data.data.SessionDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class SessionDataSourceModule {
    @Binds
    abstract fun bindSessionDataSource(sessionDataSource: SessionDataSourceImpl):
            SessionDataSource
}