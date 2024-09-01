package com.exco.hostapp.integration.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
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
import com.exco.hostapp.integration.fragments.views.TopNavigation
import com.exco.hostapp.integration.theme.MyApplicationTheme
import com.exco.hostapp.integration.util.Constants
import com.exco.hosttapp.integration.R
import com.exco.player.configuration.MiniPlayerConfiguration
import com.exco.player.configuration.PlayerConfiguration

class ConfigurationFragmentCompose : Fragment(R.layout.fragment_compose_configuration) {

    private val navController by lazy { findNavController() }

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
            var playerId by rememberSaveable { mutableStateOf(TestConfiguration.configuration.playerId) }
            var appCategory by rememberSaveable { mutableStateOf<String?>(null) }
            var appStoreId by rememberSaveable { mutableStateOf<String?>(null) }
            var appStoreUrl by rememberSaveable { mutableStateOf<String?>(null) }
            var appVersion by rememberSaveable { mutableStateOf<String?>(null) }
            var appDevices by rememberSaveable{ mutableStateOf<String?>(null) }
            var ifa by rememberSaveable { mutableStateOf(IfaUtils.ifa) }

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                TopNavigation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color(0xFF12339A))
                    ,
                    screenName = "Mini Player Configuration",
                    navController = navController
                )
                LazyColumn(
                    modifier = Modifier.padding(start = 16.dp,top = 12.dp,end = 16.dp)
                ){
                    item {
                        InputCard(
                            "AppName",
                            "AppName",
                            getApplicationName(LocalContext.current.applicationContext),
                            true
                        ) {}
                    }
                    item {
                        InputCard(
                            "AppBundle",
                            "AppBundle",
                            context?.packageName ?: "AppBundle",
                            true
                        ) {}
                    }
                    item {
                        InputCard(
                            "PlayerId",
                            "Enter your unique player ID",
                            playerId,
                            false
                        ) {
                            playerId = it
                        }
                    }
                    item {
                        InputCard(
                            "App category",
                            "Select your app's category",
                            appCategory,
                            false
                        ) {
                            appCategory = it
                        }
                    }
                    item {
                        InputCard(
                            "App Store ID",
                            "Enter your app's store id",
                            appStoreId,
                            false
                        ) {
                            appStoreId = it
                        }
                    }
                    item {
                        InputCard(
                            "App Store URL",
                            "Enter your app's store url",
                            appStoreUrl,
                            false
                        ) {
                            appStoreUrl = it
                        }
                    }
                    item {
                        InputCard(
                            "App Version",
                            "Enter your app's version number",
                            appVersion,
                            false
                        ) {
                            appVersion = it
                        }
                    }
                    item {
                        InputCard(
                            "App Devices",
                            "Select supporter devices",
                            appDevices,
                            false
                        ) {
                            appDevices = it
                        }
                    }
                    item {
                        InputCard(
                            "IFA",
                            "Enter your IFA",
                            ifa,
                            false
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
                                    isProgrammatic = false
                                )

                                navController.navigate(
                                    R.id.action_configurationFragmentCompose_to_uiMethodFragment,
                                    bundleOf(Constants.CONFIG_BUNDLE_KEY to configurations)
                                )
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
        inputName:String,
        inputTip:String,
        inputText: String?,
        readOnly:Boolean,
        changeValue:(String) -> Unit
    ){
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
                value = inputText.orEmpty(),
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
    private fun getApplicationName(context:Context): String {
        val packageName = context.packageName
        val packageManager = context.packageManager

        return try {
            val isHigherThanVersion33 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

            val applicationInfo = if (isHigherThanVersion33) {
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



