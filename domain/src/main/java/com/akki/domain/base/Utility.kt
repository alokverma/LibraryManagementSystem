package com.akki.domain.base

import com.akki.domain.enitity.ScanResult
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

object Utility {

    fun parseJSONtoScanResult(data: String?, gson: Gson, parser: JsonParser): ScanResult? {
        var scanResult: ScanResult? = null
        try {
            scanResult = gson.fromJson(parser.parse(data).asString, ScanResult::class.java)
            return scanResult
        } catch (e: UnsupportedOperationException) {
            e.printStackTrace()
        } catch (e: JsonIOException) {
            e.printStackTrace()
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            scanResult = ScanResult()
            scanResult.isInValidQrCode = true
            return scanResult
        }
        return null
    }

    fun getTimeDiff(startTime: Long, endTime: Long): Long {
        try {
            val diff: Long = endTime - startTime
            val diffMinutes = diff / (60 * 1000) % 60
            return diffMinutes
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }

    fun calculatePrice(timeInMinutes: Long, pricePerMinute: Float): Float {
        return timeInMinutes * pricePerMinute
    }

}