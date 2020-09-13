package com.akki.di.module


import com.akki.data.di.SessionRepositoryModule
import com.akki.meesholibraryassignment.di.WelcomeActivityViewModelModule
import com.akki.meesholibraryassignment.ui.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(
        modules = [
            WelcomeActivityViewModelModule::class,
            SessionRepositoryModule::class
        ]
    )

    abstract fun bindsWelcomeActivity(): WelcomeActivity

}