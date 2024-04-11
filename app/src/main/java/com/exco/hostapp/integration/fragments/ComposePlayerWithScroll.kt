package com.exco.hostapp.integration.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exco.hostapp.integration.fragments.views.TopNavigation
import com.exco.hostapp.integration.theme.MyApplicationTheme
import com.exco.hostapp.integration.util.Constants
import com.exco.hostapp.integration.util.TextUtils
import com.exco.hosttapp.integration.R
import com.exco.player.configuration.PlayerConfiguration
import com.exco.player.views.ExCoPlayerView

class ComposePlayerWithScroll : Fragment(R.layout.fragment_compose_player) {

    private val navController by lazy { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ComposeView>(R.id.composeView).setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ComposePlayerWithScrollScreen()
                }
            }
        }
    }
    @Composable
    fun ComposePlayerWithScrollScreen(
        modifier: Modifier = Modifier
    ) {
        val scrollState = rememberScrollState()
        val configuration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.CONFIG_BUNDLE_KEY, PlayerConfiguration::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(Constants.CONFIG_BUNDLE_KEY)
        }
        TopNavigation(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF12339A))
            ,
            screenName = "Compose Screen",
            navController
        )
        Column(
            modifier = modifier
                .padding(top = 56.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ExoPlayerView(
                modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 24.dp,start = 16.dp,end = 16.dp),
                configuration?: TestConfiguration.configuration
            )
            TextArea(
                modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )
        }
    }
    @Composable
    fun ExoPlayerView(modifier: Modifier,configuration: PlayerConfiguration) {
        val context = LocalContext.current
        AndroidView(
            factory = { ExCoPlayerView(context) },
            modifier = modifier
        ) { view ->
            view.loadPlayer(playerConfiguration = configuration)
//            view.publicErrorListener = object : ExCoErrorDelegate{
//                override fun error(msg: String) {
//                    super.error(msg)
//                    Log.e("ExoPlayerView",msg)
//                }
//            }
        }
    }

    @Composable
    fun TextArea(modifier: Modifier){
        Box(modifier = modifier){
            Text(
                text = TextUtils.DUMMY_TEXT,
                fontSize = 18.sp
            )
        }
    }
}
