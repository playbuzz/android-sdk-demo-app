package com.exco.hostapp.integration.fragments.recycler.adapter

import com.exco.player.configuration.PlayerConfiguration

sealed class ViewData(val type: DataType) {
    data class TextData(val text: String) : ViewData(DataType.TEXT)
    data class ExCoPlayerData(val configuration: PlayerConfiguration) : ViewData(DataType.EXO_PLAYER)
}