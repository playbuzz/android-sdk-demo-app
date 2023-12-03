package com.exco.hostapp.demo.util

import android.annotation.SuppressLint
import android.util.Log
import com.exco.player.AdMetadata
import com.exco.player.ExCoDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SampleXmlLogger(private val logger: Logger) : ExCoDelegate {
    override fun playerInit() {
        logger.log("playerInit")
    }
    override fun playerPlaying() {
        logger.log("playerPlaying")
    }
    override fun adInit() {
        logger.log("adInit")
    }
    override fun adStarted() {
        logger.log("adStarted")
    }
    override fun adImpression(metadata: AdMetadata) {
        logger.log("adImpression")
    }
    override fun playerLoad() {
        logger.log("playerLoad")
    }
    override fun playerUnmuted() {
        logger.log("playerUnmuted")
    }
    override fun adCompleted() {
        logger.log("adCompleted")
    }
    override fun adSkipped() {
        logger.log("adSkipped")
    }
    override fun playerMuted() {
        logger.log("playerMuted")
    }
    override fun playerPaused() {
        logger.log("playerPaused")
    }
    override fun adClicked() {
        logger.log("adClicked")
    }
    override fun unknownEvent(event: String) {
        logger.log("unknownEvent:$event")
    }
    override fun adFirstQuartile() {
        logger.log("adFirstQuartile")
    }
    override fun adMidpoint() {
        logger.log("adMidpoint")
    }
    override fun adThirdQuartile() {
        logger.log("adThirdQuartile")
    }
    override fun playerClosed() {
        logger.log("playerClosed")
    }
}
interface Logger {
    fun log(message: String)
}
class TextViewLogger : Logger {
    private val _logFlow = MutableStateFlow("Logs:")
    val logFlow = _logFlow.asStateFlow()

    private val stringBuilder = StringBuilder()
    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    @SuppressLint("SetTextI18n")
    override fun log(message: String) {
        val timestamp = dateFormat.format(Date())
        val formattedMessage = "$timestamp - $message\n"
        Log.i("TextViewLogger", formattedMessage)

        stringBuilder.append(formattedMessage)
        _logFlow.value = stringBuilder.toString()
    }
}