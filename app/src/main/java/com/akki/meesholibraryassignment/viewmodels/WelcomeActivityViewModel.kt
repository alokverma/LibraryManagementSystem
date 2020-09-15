package com.akki.meesholibraryassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akki.data.repository.SessionReposityImpl
import com.akki.data.utility.mapResponeToGeneric
import com.akki.domain.base.ResultWrapper
import com.akki.domain.base.Utility
import com.akki.domain.enitity.ScanResult
import com.akki.domain.enitity.SessionSubmitResult
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
    private val postSession: PostSessionUseCase
) :
    BaseViewModel() {

    private val startSessionTimeLiveData = MutableLiveData<Date>()
    private val endSessionTimeLiveData = MutableLiveData<Date>()
    private val errorResponse = MutableLiveData<Throwable>()
    private val loadingState = MutableLiveData<Boolean>()
    private val timeSpent = MutableLiveData<Long>()
    private val totalPrice = MutableLiveData<Float>()
    private val paymentStatus = MutableLiveData<SessionSubmitResult>()
    private val errorState = MutableLiveData<String>()

    private var start: Long = 0
    private var end: Long = 0

    val startDate: LiveData<Date>
        get() = startSessionTimeLiveData

    val endTime: LiveData<Date>
        get() = endSessionTimeLiveData

    val isError: LiveData<Throwable>
        get() = errorResponse

    fun showSessionIfActive(): LiveData<ScanResult> {
        return appSessionLiveData
    }

    /**
     * contains session data in pref
     */
    fun getAppSessionStateLiveData(): LiveData<String> = sessionStateLiveData

    private var tempDisposable: Disposable? = null


    fun setSessionStartOrEnd(qrCodeResult: String) {
        tempDisposable = startSession.execute(qrCodeResult).subscribe { it ->
            when (it) {
                SessionReposityImpl.SCANSTATE.SCAN_COMPLETED.ordinal -> {
                    errorState.postValue(null)
                }
                SessionReposityImpl.SCANSTATE.SCAN_FAIL.ordinal -> {
                    errorState.postValue("Scan Failed, please scan valid data")
                }
                SessionReposityImpl.SCANSTATE.SCAN_FAIL_DUE_TO_WRONG_QR_CODE.ordinal -> {
                    errorState.postValue(
                        "Invalid QRCode, Please scan same qr code that you" +
                                " used for starting session"
                    )
                }
            }
            tempDisposable?.track()
        }
    }

    private fun endSession() {
        endSession.execute("endSession")
    }

    /**
     * Post session data to server when pay clicked
     */
    fun postSession() {
        appSessionLiveData.value?.let {
            loadingState.postValue(true)
            it.totalMin = timeSpent.value!!
            it.totalPrice = Utility.calculatePrice(it.totalMin, it.pricePerMin!!.toFloat())
            tempDisposable = postSession.execute(it).mapResponeToGeneric().subscribe { result ->
                when (result) {
                    is ResultWrapper.Error -> {
                        loadingState.postValue(false)
                        errorResponse.postValue(result.throwable)
                    }
                    is ResultWrapper.Success -> {
                        loadingState.postValue(false)
                        paymentStatus.postValue(result.data)
                        endSession()
                    }
                }
            }
        }
        tempDisposable?.track()
    }


    fun getStartTime() {
        start = startSessionTime.execute("")
        startSessionTimeLiveData.postValue(Date(start))
    }

    fun getLoadingState(): LiveData<Boolean> = loadingState

    fun getEndTime() {
        end = endSessionTime.execute("")
        endSessionTimeLiveData.postValue(Date(end))
    }

    fun getTimeSpent(): LiveData<Long> {
        timeSpent.postValue(Utility.getTimeDiff(start, end))
        return timeSpent
    }

    fun totalPrice(): LiveData<Float> {
        val time = Utility.getTimeDiff(start, end)
        totalPrice.postValue(
            appSessionLiveData.value?.pricePerMin?.toFloat()
                ?.let { Utility.calculatePrice(time, it) }
        )
        return totalPrice
    }

    fun getPaymentStatusResult(): LiveData<SessionSubmitResult> = paymentStatus

    fun getErrorState(): LiveData<String> = errorState
}