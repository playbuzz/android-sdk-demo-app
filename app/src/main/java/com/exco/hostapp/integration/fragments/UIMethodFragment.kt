package com.exco.hostapp.integration.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.exco.hostapp.integration.fragments.composables.UISelectionTypeCard
import com.exco.hostapp.integration.theme.MyApplicationTheme
import com.exco.hostapp.integration.util.Constants
import com.exco.hostapp.integration.util.UiMethod

class UIMethodFragment : Fragment(R.layout.fragment_ui_method) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ComposeView>(R.id.composeView).setContent {
            MyApplicationTheme {
                IntroductionScreen()
            }
        }
    }

    @Composable
    private fun IntroductionScreen() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                IntroductionArea()
                SelectionCard()
            }
        }
    }

    @Composable
    private fun SelectionCard(
        modifier: Modifier = Modifier,
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 24.dp)
                .verticalScroll(rememberScrollState())
                .shadow(
                    elevation = 32.dp,
                    clip = true,
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp
                    )
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
                selectionName = "XML - ScrollView",
                selectionAbout = "The screen will be created from xml layout using ScrollView."
            ) {
                navigateForward(UiMethod.ScrollView)
            }
            UISelectionTypeCard(
                selectionName = "XML - RecyclerView",
                selectionAbout = "The screen will be created from xml layout using RecyclerView."
            ) {
                navigateForward(UiMethod.RecyclerView)
            }
            UISelectionTypeCard(
                selectionName = "Compose",
                selectionAbout = "The screen will be created with Jetpack Compose UI"
            ) {
                navigateForward(UiMethod.Compose)
            }
            UISelectionTypeCard(
                selectionName = "Programmatic",
                selectionAbout = "The screen will demonstrate ExCo player programmatic API"
            ) {
                navigateForward(UiMethod.Programmatic)
            }
        }
    }

    private fun navigateForward(uiMethod: UiMethod) {
        val bundle = bundleOf(Constants.UI_METHOD to uiMethod)
        findNavController().navigate(R.id.toConfigOptionFragment, bundle)
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
                text = "Hey\uD83D\uDC4B",
                color = Color(0xFF12339A),
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "Welcome to Exco App",
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
                text = "UI method selection",
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "Select Your preferred UI method",
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W400
            )
        }
    }
}