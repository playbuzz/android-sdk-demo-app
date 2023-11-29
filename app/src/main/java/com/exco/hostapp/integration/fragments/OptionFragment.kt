package com.exco.hostapp.integration.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exco.hosttapp.integration.R

class OptionFragment : Fragment(R.layout.fragment_options) {

    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ComposeView>(R.id.composeView).setContent {
            UiContent()
        }
    }

    @Composable
    fun UiContent() {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OptionButton(text = "XML Dynamic Configuration with Logs") {
                    navController.navigate(R.id.action_optionFragment_to_configurationFragment)
                }
            }
        }
    }

    @Composable
    private fun OptionButton(
        text: String,
        onClick: () -> Unit,
    ) {
        Button(
            modifier = Modifier.width(400.dp).padding(10.dp),
            onClick = onClick,
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}