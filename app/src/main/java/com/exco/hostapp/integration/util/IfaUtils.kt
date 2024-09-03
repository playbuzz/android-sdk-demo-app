package com.exco.hostapp.integration.util

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal object IfaUtils {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.i("getAdvertisingId", "Advertising ID not available")
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    var ifa: String = ""

    fun getAdvertisingId(context: Context) {
        scope.launch(exceptionHandler) {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
            val id = adInfo.id
            Log.i("getAdvertisingId", id ?: "Advertising ID not available")
            ifa = id ?: ""
        }
    }
}