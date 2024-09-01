package com.exco.hostapp.integration.fragments.utils

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.exco.hostapp.integration.util.Constants
import com.exco.player.configuration.MiniPlayerConfiguration
import com.exco.player.configuration.MiniPlayerType
import com.exco.player.configuration.PlayerConfiguration

fun NavController.navigateWithFinalConfigurationBundle(
    @IdRes composeDestination:Int,
    @IdRes xmlDestination:Int,
    playerId:String,
    appCategory:String?,
    appStoreUrl:String?,
    appVersion:String?,
    appDeviceId:String?,
    ifa:String?,
    ui_type: Int
) {
    val configuration = PlayerConfiguration(
        playerId,
        appCategory,
        appDeviceId,
        appStoreUrl,
        appVersion,
        appDeviceId,
        ifa,
        MiniPlayerConfiguration()
    )
    val payloadBundle = bundleOf(
        Constants.CONFIG_BUNDLE_KEY to configuration
    )
    if (ui_type == Constants.UI_TYPE_COMPOSE) {
        navigate(composeDestination, payloadBundle)
    } else {
        navigate(xmlDestination, payloadBundle)
    }
}
