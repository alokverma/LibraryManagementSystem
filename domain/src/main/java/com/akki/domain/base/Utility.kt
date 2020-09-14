package com.akki.domain.base

import com.akki.domain.enitity.ScanResult
import com.google.gson.Gson
import com.google.gson.JsonParser

object Utility {

     fun parseJSONtoScanResult(data: String?, gson: Gson, parser: JsonParser): ScanResult? {
        try {
            val scanResult = gson.fromJson(parser.parse(data).asString, ScanResult::class.java)
            return scanResult
        } catch (e: UnsupportedOperationException) {
            e.printStackTrace()
        }
        return null
    }

}