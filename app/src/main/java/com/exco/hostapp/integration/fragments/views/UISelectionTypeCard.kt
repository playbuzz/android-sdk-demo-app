package com.exco.hostapp.integration.fragments.views

import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UISelectionTypeCard(
    modifier: Modifier,
    selectionName:String,
    selectionAbout:String,
    onSelectionClick:() -> Unit
){
    Card(
        modifier = modifier
            .clickable {
                onSelectionClick()
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor =  Color(0xFF12339A),
            contentColor =  Color(0xFF12339A),
            disabledContainerColor =  Color(0xFF12339A),
            disabledContentColor =  Color(0xFF12339A)
        )
    ){
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                top = 32.dp,
                end = 24.dp,
                bottom = 72.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectionName,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.W600
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = selectionAbout,
                color = Color(0xFF8295C6),
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W600
            )
        }
    }
}