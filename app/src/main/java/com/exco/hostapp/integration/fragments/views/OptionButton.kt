package com.exco.hostapp.integration.fragments.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
public fun OptionButton(
    modifier: Modifier,
    buttonText:String,
    onClickNavigate:()->Unit
){
    Button(
        onClick = {
            onClickNavigate()
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        border = BorderStroke(1.dp,Color(0xFF12339A)),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xFF12339A),
            containerColor = Color(0xFFF0F4FF)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box{
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = buttonText,
                    color = Color(0xFF12339A),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    tint = Color(0xFF12339A),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}