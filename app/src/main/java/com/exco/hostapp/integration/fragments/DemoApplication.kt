package com.exco.hostapp.integration.fragments

import android.app.Application
import com.exco.hostapp.integration.util.IfaUtils
import com.exco.player.sdk.ExCoSDK

class DemoApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        ExCoSDK.activate(applicationContext)
        IfaUtils.getAdvertisingId(applicationContext)
    }
}