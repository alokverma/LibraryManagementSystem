package com.akki.meesholibraryassignment.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.akki.meesholibraryassignment.base.BaseViewModel
import com.google.zxing.integration.android.IntentResult
import javax.inject.Inject

class WelcomeActivityViewModel @Inject constructor() : BaseViewModel() {

    private val result: MutableLiveData<String> = MutableLiveData()

    fun triggerSession(result: IntentResult) {

    }
}