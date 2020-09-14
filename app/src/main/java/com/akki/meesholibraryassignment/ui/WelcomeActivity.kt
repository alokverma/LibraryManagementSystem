package com.akki.meesholibraryassignment.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akki.domain.base.SessionKeys
import com.akki.domain.base.SessionState
import com.akki.domain.enitity.ScanResult
import com.akki.meesholibraryassignment.R
import com.akki.meesholibraryassignment.viewmodels.WelcomeActivityViewModel
import com.google.zxing.integration.android.IntentIntegrator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.qr_content_layout.*
import javax.inject.Inject


class WelcomeActivity : DaggerAppCompatActivity() {

    private var qrScan: IntentIntegrator? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WelcomeActivityViewModel
    private var chronoMeter: ChronoMeterView? = null

    @Inject
    lateinit var myPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        qrScan = IntentIntegrator(this)
        chronoMeter = ChronoMeterView.getInstance(
            chronometerView,
            myPref
        )
        chronoMeter?.setInHourFormat()
        initiateViewModel()
        observeChanges()
        btn_start_scan.setOnClickListener {
            if (btn_start_scan.text.toString() != "Pay")
                qrScan?.initiateScan();
            else {
                viewModel.postSession()
                //
            }
        }

    }

    /**
     * set QR result on ui
     * @param data contains all result
     */
    private fun setQRContentData(data: ScanResult?) {
        data?.run {
            ll_content.visibility = View.VISIBLE
            tv_location_id.text = getString(R.string.location_id) + data?.locationId
            tv_location_details.text = getString(R.string.location_details) + data?.locationDetails
            tv_price.text = getString(R.string.price) + data?.pricePerMin
            if (myPref.getInt(
                    SessionKeys.SESSION_STATE,
                    SessionState.END_SESSION.ordinal
                ) == SessionState.STARTED.ordinal
            ) {
                showStartSessionDetails(true)
                tv_start_time.text =
                    "start time"
                if (!chronoMeter?.isRunning!!)
                    chronoMeter?.startChronometer()
                else
                    chronoMeter?.resumeState()
                btn_start_scan.text = "End Session"
            } else if (myPref.getInt(
                    SessionKeys.SESSION_STATE,
                    SessionState.END_SESSION.ordinal
                ) == SessionState.PAYMENT_SESSION.ordinal
            ) {
                tv_end_time.text = "endTime"
                tv_payment.text = "pay this amount"
                btn_start_scan.text = "Pay"
                chronoMeter?.pauseChronometer()
            } else {
                showStartSessionDetails(false)
                tv_end_time.text = "endTime"
                tv_payment.text = "pay this amount"
                btn_start_scan.text = "Start Scan"
                hideContentViewAndResetTimer()
            }
        }

        if (data == null) ll_content.visibility = View.GONE

    }

    private fun showStartSessionDetails(show: Boolean) {
        if (show) {
            tv_start_time.visibility = View.VISIBLE
            tv_location_id.visibility = View.VISIBLE
            tv_location_details.visibility = View.VISIBLE
            tv_price.visibility = View.VISIBLE
            tv_end_time.visibility = View.GONE
            tv_payment.visibility = View.GONE
        } else {
            tv_end_time.visibility = View.VISIBLE
            tv_payment.visibility = View.VISIBLE
            tv_start_time.visibility = View.GONE
            tv_location_id.visibility = View.GONE
            tv_location_details.visibility = View.GONE
            tv_price.visibility = View.GONE
        }

    }

    /**
     * observe changes in live data if session updates
     */

    private fun observeChanges() {
        viewModel.isError.observe(this, {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show()
        })
        viewModel.showSessionIfActive().observe(this, {
            setQRContentData(it)
        })
    }

    private fun hideContentViewAndResetTimer() {
        chronoMeter?.stopChronometer()
    }

    private fun initiateViewModel() {
        viewModel = ViewModelProvider(this, modelFactory)
            .get(WelcomeActivityViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            viewModel.setSessionStartOrEnd(
                result.contents
            )
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}