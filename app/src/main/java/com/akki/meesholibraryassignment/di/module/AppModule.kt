package com.akki.di.module

import android.app.Application
import android.content.Context
import com.akki.data.utility.SchedulersFacade
import com.akki.data.utility.SchedulersProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindContext(application: Application): Context


    @Singleton
    @Binds
    abstract fun providerScheduler(schedulersFacade: SchedulersFacade): SchedulersProvider
}