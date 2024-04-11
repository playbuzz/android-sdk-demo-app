package com.exco.hostapp.integration.fragments

import android.app.Application
import android.content.Context
import android.util.Log
import com.exco.player.sdk.ExCoSDK
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DemoApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        ExCoSDK.activate(applicationContext)
        IfaUtils.getAdvertisingId(applicationContext)
    }
}
internal object IfaUtils {
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.i("getAdvertisingId", ifa ?: "Advertising ID not available")
    }
    private val scope = CoroutineScope(Dispatchers.IO)
    var ifa:String? = null

    fun getAdvertisingId(context: Context) {
        scope.launch(exceptionHandler) {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
            val id = adInfo.id
            Log.i("getAdvertisingId", id ?: "Advertising ID not available")
            ifa = id
        }
    }
}