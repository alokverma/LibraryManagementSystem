package com.akki.meesholibraryassignment.di

import android.app.Application
import com.akki.data.di.NetworkModule
import com.akki.data.di.module.ApiModule
import com.akki.di.module.ActivityBindingModule
import com.akki.di.module.AppModule
import com.akki.di.module.ViewModelFactoryModule
import com.akki.meesholibraryassignment.MeeshoApplication
import com.akki.meesholibraryassignment.di.module.SharedPrefModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        AppModule::class,
        ApiModule::class,
        SharedPrefModule::class
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