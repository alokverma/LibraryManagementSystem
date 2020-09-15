package com.akki.data.di

import com.akki.data.repository.SessionReposityImpl
import com.akki.domain.repository.SessionRepository
import dagger.Binds
import dagger.Module

@Module
abstract class SessionRepositoryModule {
    @Binds
    abstract fun getSessionRepo(sessionRepository: SessionReposityImpl): SessionRepository
}