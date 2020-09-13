package com.akki.meesholibraryassignment.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akki.meesholibraryassignment.R
import com.akki.meesholibraryassignment.viewmodels.WelcomeActivityViewModel
import com.google.zxing.integration.android.IntentIntegrator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class WelcomeActivity : DaggerAppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 200
    private var qrScan: IntentIntegrator? = null
    private val mCameraId = -1

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WelcomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        qrScan = IntentIntegrator(this);
        qrScan?.setDesiredBarcodeFormats(listOf("QR_CODE"))
        initiateViewModel()
        btn_start_scan.setOnClickListener { qrScan?.initiateScan(); }
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


//    private fun checkPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.CAMERA
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestPermission() {
//        ActivityCompat.requestPermissions(
//            this, arrayOf(Manifest.permission.CAMERA),
//            PERMISSION_REQUEST_CODE
//        )
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
//
//            } else {
//                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        showMessageOKCancel(
//                            "You need to allow access permissions"
//                        ) { _, _ ->
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                requestPermission()
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
//        AlertDialog.Builder(this@MainActivity)
//            .setMessage(message)
//            .setPositiveButton("OK", okListener)
//            .setNegativeButton("Cancel", null)
//            .create()
//            .show()
//    }
}