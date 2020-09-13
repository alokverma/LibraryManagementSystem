package com.akki.meesholibraryassignment.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.akki.data.utility.mapResponeToGeneric
import com.akki.domain.base.ResultWrapper
import com.akki.domain.usecase.EndSession
import com.akki.domain.usecase.StartSession
import com.akki.meesholibraryassignment.base.BaseViewModel
import com.google.zxing.integration.android.IntentResult
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class WelcomeActivityViewModel @Inject constructor(
    private val startSession: StartSession,
    private val endSession: EndSession,
) :
    BaseViewModel() {

    private val TAG = WelcomeActivityViewModel::class.java.name
    private val result: MutableLiveData<String> = MutableLiveData()
    private var tempDisposable: Disposable? = null

    fun startSession(result: String) {
        tempDisposable =
            startSession.execute(result).mapResponeToGeneric().subscribe { scanResult ->
                when (scanResult) {
                    is ResultWrapper.Error -> {
                        Log.e(TAG, scanResult.throwable.toString())
                    }
                    is ResultWrapper.Success -> {
                        Log.e(TAG, scanResult.data.toString())
                    }
                }
            }
        tempDisposable?.track()
    }

    fun endSession(result: IntentResult) {
        endSession.execute("")
    }

    fun postSession(result: String) {
        // postSessionUseCase.execute(result)
    }
}