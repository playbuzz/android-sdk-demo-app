package com.exco.hostapp.integration.fragments.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
@Composable
fun UISelectionDescription(
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