package com.akki.di.module


import com.akki.meesholibraryassignment.ui.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(
        modules = [
        ]
    )
    abstract fun bindsDashboardActivity(): WelcomeActivity

}