package com.akki.meesholibraryassignment.di

import androidx.lifecycle.ViewModel
import com.akki.meesholibraryassignment.viewmodels.ViewModelKey
import com.akki.meesholibraryassignment.viewmodels.WelcomeActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WelcomeActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeActivityViewModel::class)
    internal abstract fun bindDashboardViewModel(viewModel: WelcomeActivityViewModel): ViewModel

}