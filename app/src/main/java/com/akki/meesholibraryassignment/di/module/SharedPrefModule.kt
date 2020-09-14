package com.akki.meesholibraryassignment.di.module

import android.content.Context
import android.content.SharedPreferences
import com.akki.data.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefModule {

    @Singleton
    @Provides
    fun provideSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    }

}