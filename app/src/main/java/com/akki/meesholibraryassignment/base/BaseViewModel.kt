package com.akki.meesholibraryassignment.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val mDisposable = CompositeDisposable()

    protected fun Disposable.track() {
        mDisposable.add(this)
    }

    override fun onCleared() {
        mDisposable.clear()
        super.onCleared()
    }
}