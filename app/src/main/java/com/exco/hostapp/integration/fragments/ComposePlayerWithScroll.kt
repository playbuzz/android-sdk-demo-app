package com.exco.hostapp.integration.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exco.hostapp.integration.fragments.composables.TopNavigation
import com.exco.hostapp.integration.theme.MyApplicationTheme
import com.exco.hostapp.integration.util.TextUtils
import com.exco.hostapp.integration.R
import com.exco.hostapp.integration.util.Constants
import com.exco.player.configuration.PlayerConfiguration
import com.exco.player.views.ExCoPlayerView

class ComposePlayerWithScroll : Fragment(R.layout.fragment_compose_player) {

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
    private fun ComposePlayerWithScrollScreen(
        modifier: Modifier = Modifier
    ) {
        val configuration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.CONFIG_BUNDLE_KEY, PlayerConfiguration::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(Constants.CONFIG_BUNDLE_KEY)
        } ?: TestConfiguration.configuration

        TopNavigation(
            screenName = "Compose Screen",
            navController = findNavController()
        )

        Column(
            modifier = modifier
                .padding(top = 56.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ExoPlayerView(configuration = configuration)

            TextArea()
        }
    }

    @Composable
    fun ExoPlayerView(modifier: Modifier = Modifier, configuration: PlayerConfiguration) {
        val context = LocalContext.current
        AndroidView(
            factory = { ExCoPlayerView(context) },
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) { view ->
            view.loadPlayer(playerConfiguration = configuration)
        }
    }

    @Composable
    fun TextArea(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        ) {
            Text(
                text = TextUtils.DUMMY_TEXT,
                fontSize = 18.sp
            )
        }
    }
}
