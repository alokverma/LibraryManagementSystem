package com.akki.meesholibraryassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akki.domain.enitity.ScanResult
import com.akki.domain.usecase.EndSession
import com.akki.domain.usecase.StartSession
import com.akki.meesholibraryassignment.base.BaseViewModel
import com.akki.meesholibraryassignment.session.AppSessionLiveData
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class WelcomeActivityViewModel @Inject constructor(
    private val startSession: StartSession,
    private val endSession: EndSession,
    private val appSessionLiveData: AppSessionLiveData,
) :
    BaseViewModel() {

    private val startOrEndSessionLiveData = MutableLiveData<String>()



    private val errorResponse: MutableLiveData<Throwable> = MutableLiveData()
    val isError: LiveData<Throwable>
        get() = errorResponse

    fun showSessionIfActive(): LiveData<ScanResult> {
        return appSessionLiveData
    }

    private var tempDisposable: Disposable? = null


//    private val sessionResult = Transformations.switchMap(
//        startOrEndSessionLiveData
//    ) {
//        startSession.execute(it)
//        appSessionLiveData
//    }

    fun setSessionStartOrEnd(qrCodeResult: String) {
        startSession.execute(qrCodeResult)
        // startOrEndSessionLiveData.postValue(qrCodeResult)
    }

    fun endSession() {
        endSession.execute("endSession")
    }

    fun postSession() {
        // postSessionUseCase.execute(result)
        endSession()
    }


    //  fun getQRCodeResult() = sessionResult
}