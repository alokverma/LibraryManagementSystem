package com.akki.meesholibraryassignment.di

import android.app.Application
import com.akki.di.module.ActivityBindingModule
import com.akki.di.module.ViewModelFactoryModule
import com.akki.meesholibraryassignment.MeeshoApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelFactoryModule::class,
    ]
)
interface AppComponent : AndroidInjector<MeeshoApplication> {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}