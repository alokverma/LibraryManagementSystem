package com.akki.meesholibraryassignment.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var myPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myPref = getSharedPreferences("MeeshoPref", Context.MODE_PRIVATE)
        qrScan = IntentIntegrator(this)
        initiateViewModel()
        setQRContent()
        btn_start_scan.setOnClickListener { qrScan?.initiateScan(); }
    }

    private fun setQRContent() {
        viewModel.getQRCodeResult().observe(this, {
            btn_start_scan.text = getString(R.string.end_session)
            tv_location_id.text = getString(R.string.location_id) + it.locationId
            tv_location_details.text = getString(R.string.location_details) + it.locationDetails
            tv_price.text = getString(R.string.price) + it.pricePerMin
            ChronoMeterView.getInstance(chronometerView, "mainChronometer", myPref)
                .startChronometer()
        })
    }

    private fun initiateViewModel() {
        viewModel = ViewModelProvider(this, modelFactory)
            .get(WelcomeActivityViewModel::class.java)

        viewModel.isError.observe(this, {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show()
        })

        viewModel.getQRCodeResult().observe(this, {
            Toast.makeText(this, it.locationDetails, Toast.LENGTH_LONG).show()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            viewModel.setSessionStartOrEnd(result.contents)
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun postSession(result: String) {
        viewModel.postSession(result)
    }
}