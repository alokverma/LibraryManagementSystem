package com.akki.meesholibraryassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.akki.data.utility.mapResponeToGeneric
import com.akki.domain.base.ResultWrapper
import com.akki.domain.enitity.ScanResult
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

    private val startOrEndSessionLiveData = MutableLiveData<String>()

    private val scanResultLiveData: MutableLiveData<ScanResult> = MutableLiveData()
    private val errorResponse: MutableLiveData<Throwable> = MutableLiveData()
    val isError: LiveData<Throwable>
        get() = errorResponse

    private var tempDisposable: Disposable? = null

//    fun startSession(result: String) {
//        tempDisposable =
//            startSession.execute(result).mapResponeToGeneric().subscribe { scanResult ->
//                when (scanResult) {
//                    is ResultWrapper.Error -> {
//                        errorResponse.postValue(scanResult.throwable)
//                    }
//                    is ResultWrapper.Success -> {
//                        scanResultLiveData.postValue(scanResult.data)
//                    }
//                }
//            }
//        tempDisposable?.track()
//    }

    private val sessionResult = Transformations.switchMap(
        startOrEndSessionLiveData
    ) {
        tempDisposable =
            startSession.execute(it).mapResponeToGeneric().subscribe { scanResult ->
                when (scanResult) {
                    is ResultWrapper.Error -> {
                        errorResponse.postValue(scanResult.throwable)
                    }
                    is ResultWrapper.Success -> {
                        scanResultLiveData.postValue(scanResult.data)
                    }
                }
            }

        tempDisposable?.track()
        scanResultLiveData
    }

    fun setSessionStartOrEnd(qrCodeResult: String) {
        startOrEndSessionLiveData.postValue(qrCodeResult)
    }

    fun endSession(result: IntentResult) {
        endSession.execute("")
    }

    fun postSession(result: String) {
        // postSessionUseCase.execute(result)
    }

    fun getQRCodeResult() = sessionResult
}