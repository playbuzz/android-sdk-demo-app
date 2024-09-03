package com.exco.hostapp.integration.fragments

import com.exco.player.configuration.MiniPlayerConfiguration
import com.exco.player.configuration.PlayerConfiguration

object TestConfiguration {
    private const val TEST_PLAYER_ID = "aa78396a-933d-4c5a-b22a-af451047961c"
    private const val TEST_APP_CATEGORY = "Sport"
    private const val TEST_APP_STORE_ID = "512412"
    private const val TEST_APP_STORE_URL = "https://test_app_store_url"
    private const val TEST_APP_VERSION = "1.0.0"
    private const val TEST_DEVICE_ID = "85958-5019f3a2-76f8"
    private const val TEST_IFA = "8a9f3a276f88a9f3a276f8"
    private val TEST_MINI_PLAYER_CONFIG = MiniPlayerConfiguration()

    /**
     * Test configuration for use in testing scenarios.
     */
    val configuration = PlayerConfiguration(
        playerId = TEST_PLAYER_ID,
        appCategory = TEST_APP_CATEGORY,
        appStoreId = TEST_APP_STORE_ID,
        appStoreUrl = TEST_APP_STORE_URL,
        appVersion = TEST_APP_VERSION,
        deviceId = TEST_DEVICE_ID,
        ifa = TEST_IFA,
        miniPlayerConfiguration = TEST_MINI_PLAYER_CONFIG
    )
}