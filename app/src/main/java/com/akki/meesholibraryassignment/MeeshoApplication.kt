package com.akki.meesholibraryassignment

import com.akki.meesholibraryassignment.di.AppComponent
import com.akki.meesholibraryassignment.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MeeshoApplication : DaggerApplication() {

    lateinit var appComponent: AppComponent
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        return appComponent
    }
}