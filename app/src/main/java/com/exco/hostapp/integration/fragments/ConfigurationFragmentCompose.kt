package com.exco.hostapp.integration.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exco.hostapp.integration.R
import com.exco.hostapp.integration.fragments.composables.TopNavigation
import com.exco.hostapp.integration.theme.MyApplicationTheme
import com.exco.hostapp.integration.util.Constants
import com.exco.hostapp.integration.util.IfaUtils
import com.exco.hostapp.integration.util.UiMethod
import com.exco.player.configuration.MiniPlayerConfiguration
import com.exco.player.configuration.PlayerConfiguration

class ConfigurationFragmentCompose : Fragment(R.layout.fragment_compose_configuration) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ComposeView>(R.id.composeView).setContent {
            MyApplicationTheme {
                ConfigurationScreen()
            }
        }
    }

    @Composable
    fun ConfigurationScreen() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val uiMethod = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getSerializable(Constants.UI_METHOD, UiMethod::class.java)
            } else {
                @Suppress("DEPRECATION")
                requireArguments().getSerializable(Constants.CONFIG_BUNDLE_KEY)
            } as UiMethod

            var playerId by rememberSaveable { mutableStateOf(TestConfiguration.configuration.playerId) }
            var appCategory by rememberSaveable { mutableStateOf("") }
            var appStoreId by rememberSaveable { mutableStateOf("") }
            var appStoreUrl by rememberSaveable { mutableStateOf("") }
            var appVersion by rememberSaveable { mutableStateOf("") }
            var appDevices by rememberSaveable { mutableStateOf("") }
            var ifa by rememberSaveable { mutableStateOf(IfaUtils.ifa) }

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                TopNavigation(
                    screenName = "Player Configuration",
                    navController = findNavController()
                )
                LazyColumn(
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 16.dp)
                ) {
                    item {
                        InputCard(
                            inputName = "AppName",
                            inputTip = "AppName",
                            inputText = getApplicationName(LocalContext.current.applicationContext),
                            readOnly = true
                        )
                    }
                    item {
                        InputCard(
                            inputName = "AppBundle",
                            inputTip = "AppBundle",
                            inputText = context?.packageName ?: "AppBundle",
                            readOnly = true
                        )
                    }
                    item {
                        InputCard(
                            inputName = "PlayerId",
                            inputTip = "Enter your unique player ID",
                            inputText = playerId
                        ) {
                            playerId = it
                        }
                    }
                    item {
                        InputCard(
                            "App category",
                            "Select your app's category",
                            appCategory
                        ) {
                            appCategory = it
                        }
                    }
                    item {
                        InputCard(
                            "App Store ID",
                            "Enter your app's store id",
                            appStoreId
                        ) {
                            appStoreId = it
                        }
                    }
                    item {
                        InputCard(
                            "App Store URL",
                            "Enter your app's store url",
                            appStoreUrl
                        ) {
                            appStoreUrl = it
                        }
                    }
                    item {
                        InputCard(
                            "App Version",
                            "Enter your app's version number",
                            appVersion
                        ) {
                            appVersion = it
                        }
                    }
                    item {
                        InputCard(
                            "App Devices",
                            "Select supporter devices",
                            appDevices
                        ) {
                            appDevices = it
                        }
                    }
                    item {
                        InputCard(
                            "IFA",
                            "Enter your IFA",
                            ifa
                        ) {
                            ifa = it
                        }
                    }
                    item {
                        Button(
                            onClick = {
                                val configurations = PlayerConfiguration(
                                    playerId = playerId,
                                    appCategory = appCategory,
                                    appStoreId = appStoreId,
                                    appStoreUrl = appStoreUrl,
                                    appVersion = appVersion,
                                    deviceId = appDevices,
                                    ifa = ifa,
                                    miniPlayerConfiguration = MiniPlayerConfiguration(),
                                    isProgrammatic = uiMethod == UiMethod.Programmatic
                                )

                                val args = bundleOf(Constants.CONFIG_BUNDLE_KEY to configurations)

                                val navId = when (uiMethod) {
                                    UiMethod.ScrollView -> {
                                        R.id.configFragmentToPlayerFragmentWithScroll
                                    }
                                    UiMethod.RecyclerView -> {
                                        R.id.configFragmentToPlayerFragmentWithRecycler
                                    }
                                    UiMethod.Compose -> {
                                        R.id.configFragmentToComposePlayerWithScroll
                                    }
                                    UiMethod.Programmatic -> {
                                        R.id.configFragmentToProgrammaticPlayerFragment
                                    }
                                }

                                findNavController().navigate(navId, args)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 32.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                ),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color(0xFF12339A),
                                containerColor = Color(0xFF12339A)
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(
                                text = "Next",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Default,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.W600,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun InputCard(
        inputName: String,
        inputTip: String,
        inputText: String,
        readOnly: Boolean = false,
        changeValue: (String) -> Unit = {}
    ) {
        Column(modifier = Modifier.padding(top = 20.dp)) {
            Text(
                text = inputName,
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W500,
            )
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    changeValue(it)
                },
                readOnly = readOnly,
                singleLine = true,
                placeholder = {
                    Text(
                        inputTip,
                        color = Color(0xFFACACAC)
                    )
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(55.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF9FAFB),
                    unfocusedContainerColor = Color(0xFFF9FAFB),
                    disabledContainerColor = Color(0xFFF9FAFB),
                    errorContainerColor = Color(0xFFF9FAFB),
                    focusedIndicatorColor = Color(0xFFD1D5DB),
                    disabledIndicatorColor = Color(0xFFD1D5DB),
                    unfocusedIndicatorColor = Color(0xFFD1D5DB)
                )
            )
        }
    }

    private fun getApplicationName(context: Context): String {
        val packageName = context.packageName
        val packageManager = context.packageManager

        return try {
            val tiramisuOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

            val applicationInfo = if (tiramisuOrHigher) {
                packageManager.getApplicationInfo(
                    packageName,
                    PackageManager.ApplicationInfoFlags.of(0)
                )
            } else {
                packageManager.getApplicationInfo(packageName, 0)
            }

            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown"
        }
    }
}



