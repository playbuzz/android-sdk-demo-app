package com.exco.hostapp.integration.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exco.hostapp.integration.R
import com.exco.hostapp.integration.fragments.composables.TopNavigation
import com.exco.hostapp.integration.fragments.composables.UISelectionTypeCard
import com.exco.hostapp.integration.theme.MyApplicationTheme
import com.exco.hostapp.integration.util.Constants
import com.exco.hostapp.integration.util.UiMethod

class ConfigOptionFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ComposeView>(R.id.composeView).setContent {
            MyApplicationTheme {
                IntroductionScreen()
            }
        }
    }

    @Composable
    fun IntroductionScreen(modifier: Modifier = Modifier) {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                TopNavigation(
                    screenName = "Configuration Options",
                    navController = findNavController()
                )
                IntroductionArea()
                SelectionCard()
            }
        }
    }

    @Composable
    private fun SelectionCard(modifier: Modifier = Modifier) {
        val uiMethod = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(Constants.UI_METHOD, UiMethod::class.java)
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getSerializable(Constants.CONFIG_BUNDLE_KEY)
        } as UiMethod

        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .shadow(
                    elevation = 32.dp,
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp
                    ),
                    clip = true
                ),
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFEFEFE),
                contentColor = Color(0xFFFEFEFE),
                disabledContainerColor = Color(0xFFFEFEFE),
                disabledContentColor = Color(0xFFFEFEFE)
            )
        ) {
            UISelectionDescription()
            UISelectionTypeCard(
                selectionName = "Predefined",
                selectionAbout = "PlayerConfiguration defined in XML attributes."
            ) {
                when (uiMethod) {
                    UiMethod.ScrollView -> {
                        findNavController().navigate(R.id.configOptionFragmentToPlayerFragmentWithScroll)
                    }
                    UiMethod.RecyclerView -> {
                        findNavController().navigate(R.id.configOptionFragmentToPlayerFragmentWithRecycler)
                    }
                    UiMethod.Compose -> {
                        findNavController().navigate(R.id.configOptionFragmentToComposePlayerWithScroll)
                    }
                    UiMethod.Programmatic -> {
                        findNavController().navigate(R.id.configOptionFragmentToProgrammaticPlayerFragment)
                    }
                }
            }
            UISelectionTypeCard(
                selectionName = "Manual",
                selectionAbout = "PlayerConfiguration needs to be defined and passed with a function loadPlayer()."
            ) {
                findNavController().navigate(
                    R.id.toConfigurationFragment,
                    bundleOf(Constants.UI_METHOD to uiMethod)
                )
            }
        }
    }

    @Composable
    private fun IntroductionArea(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.padding(
                top = 48.dp,
                start = 16.dp
            )
        ) {
            Text(
                text = "Let's go",
                color = Color(0xFF12339A),
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "Move forward with options",
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W500
            )
        }
    }

    @Composable
    private fun UISelectionDescription(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                top = 36.dp
            )
        ) {
            Text(
                text = "Configuration Input selection",
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "Select Your preferred Configuration Input method",
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W400
            )
        }
    }
}