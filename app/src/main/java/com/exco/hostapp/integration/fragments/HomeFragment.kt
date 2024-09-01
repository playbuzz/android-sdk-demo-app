package com.exco.hostapp.integration.fragments

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
import com.exco.hostapp.integration.fragments.views.UISelectionTypeCard
import com.exco.hostapp.integration.theme.MyApplicationTheme
import com.exco.hostapp.integration.util.Constants
import com.exco.hosttapp.integration.R

class HomeFragment: Fragment(R.layout.fragment_home) {

    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ComposeView>(R.id.composeView).setContent {
            MyApplicationTheme {
                IntroductionScreen()
            }
        }
    }

    @Composable
    fun IntroductionScreen() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                IntroductionArea(
                    modifier = Modifier.padding(
                        top = 48.dp,
                        start = 16.dp
                    ),
                    "Hey\uD83D\uDC4B",
                    "Welcome to Exco App"
                )
                SelectionCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp)
                        .shadow(
                            elevation = 32.dp,
                            shape = RoundedCornerShape(
                                topStart = 32.dp,
                                topEnd = 32.dp
                            ),
                            clip = true
                        )
                )
            }
        }
    }
    @Composable
    private fun SelectionCard(
        modifier: Modifier,
    ){
        Card(
            modifier = modifier,
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
            UISelectionDescription(modifier = Modifier.padding(
                start = 24.dp,
                top = 36.dp
                ),
                "Configuration Input selection",
                "Select Your preferred Configuration Input method"
            )
            UISelectionTypeCard(
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 36.dp,
                    end = 24.dp
                ),
                "Predefined",
                "PlayerConfiguration defined in XML attributes."
            ) {
                navController.navigate(R.id.action_homeFragment_to_uiMethodFragment)
            }
            UISelectionTypeCard(
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 36.dp,
                    end = 24.dp
                ),
                "Manual",
                "PlayerConfiguration needs to be defined and passed with a function loadPlayer()."
            ) {
                navController.navigate(R.id.action_homeFragment_to_configurationFragmentCompose)
            }
        }
    }

    @Composable
    private fun IntroductionArea(
        modifier: Modifier,
        selectionName: String,
        selectionAbout: String
    ){
        Column(
            modifier = modifier
        ) {
            Text(
                text = selectionName,
                color = Color(0xFF12339A),
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W600
            )
            Text(
                text = selectionAbout,
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W500
            )
        }
    }
    @Composable
    private fun UISelectionDescription(
        modifier: Modifier,
        selectionName: String,
        selectionAbout: String
    ){
        Column(
            modifier = modifier
        ) {
            Text(
                text = selectionName,
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W600
            )
            Text(
                text = selectionAbout,
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W400
            )
        }
    }
}