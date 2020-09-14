package com.akki.meesholibraryassignment.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akki.domain.enitity.ScanResult
import com.akki.domain.usecase.*
import com.akki.meesholibraryassignment.base.BaseViewModel
import com.akki.meesholibraryassignment.session.AppSessionLiveData
import com.akki.meesholibraryassignment.session.AppSessionState
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject

class WelcomeActivityViewModel @Inject constructor(
    private val startSession: StartSession,
    private val endSession: EndSession,
    private val appSessionLiveData: AppSessionLiveData,
    private val startSessionTime: GetStartSessionTime,
    private val endSessionTime: GetEndSessionTime,
    private val sessionStateLiveData: AppSessionState,
    private val inValidScanUseCase: InvalidScanUseCase
) :
    BaseViewModel() {

    val startDate: LiveData<Date>
        get() = startSessionTimeLiveData

    val endTime: LiveData<Date>
        get() = endSessionTimeLiveData

    private val startSessionTimeLiveData = MutableLiveData<Date>()
    private val endSessionTimeLiveData = MutableLiveData<Date>()

    val invalidScan: LiveData<String>
        get() = invalidScanLiveDaata

    private val invalidScanLiveDaata = MutableLiveData<String>()

    val errorResponse: MutableLiveData<Throwable> = MutableLiveData()

    val isError: LiveData<Throwable>
        get() = errorResponse

    fun showSessionIfActive(): LiveData<ScanResult> {
        return appSessionLiveData
    }


    fun getAppSessionStateLiveData(): LiveData<String> = sessionStateLiveData

    private var tempDisposable: Disposable? = null


    fun setSessionStartOrEnd(qrCodeResult: String) {
        startSession.execute(qrCodeResult)
    }

    fun endSession() {
        endSession.execute("endSession")
    }

    fun postSession() {
        // postSessionUseCase.execute(result)
        endSession()
    }

    fun getStartTime() {
        val startTime = startSessionTime.execute("")
        startSessionTimeLiveData.postValue(Date(startTime))
    }

    fun getEndTime() {
        endSessionTimeLiveData.postValue(Date(endSessionTime.execute("")))
    }

    fun checkIfScanIsValid() {
        tempDisposable = inValidScanUseCase.execute("").subscribe {
            Log.e("taga", it.toString())
            invalidScanLiveDaata.postValue(it)
        }
        tempDisposable?.track()
    }
}