package com.akki.meesholibraryassignment.ui

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akki.domain.enitity.ScanResult
import com.akki.domain.session.SessionKeys
import com.akki.domain.session.SessionState
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
    private var dialog: ProgressDialog? = null

    @Inject
    lateinit var sessionPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        qrScan = IntentIntegrator(this)
        chronoMeter = ChronoMeterView.getInstance(
            chronometerView,
            sessionPref
        )
        chronoMeter?.setInHourFormat()
        initiateViewModel()
        observeChanges()
        viewModel.checkIfScanIsValid()
        btn_start_scan.setOnClickListener {
            if (sessionPref.getInt(
                    SessionKeys.SESSION_STATE,
                    SessionState.END_SESSION.ordinal
                ) == SessionState.PAYMENT_SESSION.ordinal
            ) {
                viewModel.postSession()
            } else {
                qrScan?.initiateScan();
            }

        }

    }

    /**
     * set QR result on ui
     * @param data contains all scan result
     */
    private fun setQRContentData(data: ScanResult?) {
        data?.run {
            ll_content.visibility = View.VISIBLE
            tv_location_id.text = getString(R.string.location_id) + data?.locationId
            tv_location_details.text = getString(R.string.location_details) + data?.locationDetails
            tv_price.text = getString(R.string.price) + data?.pricePerMin
            if (sessionPref.getInt(
                    SessionKeys.SESSION_STATE,
                    SessionState.END_SESSION.ordinal
                ) == SessionState.STARTED.ordinal
            ) {
                showStartSessionDetails(true)
                attachStartSessionConent()

            } else if (sessionPref.getInt(
                    SessionKeys.SESSION_STATE,
                    SessionState.END_SESSION.ordinal
                ) == SessionState.PAYMENT_SESSION.ordinal
            ) {
                showStartSessionDetails(false)
                attachPayementDetails()
            } else {
                showStartSessionDetails(false)
                chronoMeter?.stopChronometer()
            }
        }

        if (data == null) ll_content.visibility = View.GONE

    }

    /**
     * show and hide the content based on session state
     */
    private fun showStartSessionDetails(show: Boolean) {
        if (show) {
            tv_start_time.visibility = View.VISIBLE
            tv_location_id.visibility = View.VISIBLE
            tv_location_details.visibility = View.VISIBLE
            tv_price.visibility = View.VISIBLE
            tv_end_time.visibility = View.GONE
            tv_total_time.visibility = View.GONE
            tv_payment.visibility = View.GONE
        } else {
            tv_end_time.visibility = View.VISIBLE
            tv_total_time.visibility = View.VISIBLE
            tv_payment.visibility = View.VISIBLE
            tv_start_time.visibility = View.VISIBLE
            tv_location_id.visibility = View.GONE
            tv_location_details.visibility = View.GONE
            tv_price.visibility = View.GONE
        }

    }

    /**
     * show started session content
     */
    private fun attachStartSessionConent() {
        viewModel.getStartTime()
        viewModel.startDate.observe(this@WelcomeActivity, {
            tv_start_time.text =
                getString(R.string.start_time) + it
        })
        if (!chronoMeter?.isRunning!!)
            chronoMeter?.startChronometer()
        else
            chronoMeter?.resumeState()
    }

    /**
     * show payement information
     */
    private fun attachPayementDetails() {
        viewModel.getStartTime()
        viewModel.getEndTime()
        viewModel.getTimeSpent().observe(this, {
            tv_total_time.text = resources.getString(R.string.total_time_spent) + it?.toString()
        })

        viewModel.totalPrice().observe(this, {
            tv_payment.text = getString(R.string.pay) + it?.toString()
        })

        viewModel.startDate.observe(this, {
            tv_start_time.text =
                getString(R.string.start_time) + it
        })

        viewModel.endTime.observe(this@WelcomeActivity, {
            tv_end_time.text =
                getString(R.string.end_time) + it + getString(R.string.minutes)
        })

        chronoMeter?.pauseChronometer()
    }

    /**
     * observe changes in live data if session updates
     */
    private fun observeChanges() {
        viewModel.isError.observe(this, {
            Toast.makeText(
                this,
                resources.getString(R.string.something_went_wrong),
                Toast.LENGTH_LONG
            ).show()
        })
        viewModel.showSessionIfActive().observe(this, {
            setQRContentData(it)
        })

        viewModel.getAppSessionStateLiveData().observe(this, {
            btn_start_scan.text = it
        })

        viewModel.invalidScan.observe(this, {

        })

        viewModel.getLoadingState().observe(this, {
            if (it) {
                dialog = ProgressDialog.show(
                    WelcomeActivity@ this, "",
                    getString(R.string.loading), true
                )
            } else {
                dialog?.dismiss()
            }
        })

        viewModel.getPaymentStatusResult().observe(this, { paymentInfo ->
            if (paymentInfo.success) {
                Toast.makeText(
                    WelcomeActivity@ this,
                    getString(R.string.successfully_submit),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun initiateViewModel() {
        viewModel = ViewModelProvider(this, modelFactory)
            .get(WelcomeActivityViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            viewModel.setSessionStartOrEnd(
                result.contents
            )
            if (result.contents == null) {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}