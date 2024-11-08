package com.littlelemon.littlelemon.view.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    //TODO: Insert code here - DONE
    /*h1*/
    titleLarge = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = LittleLemonColor.charcoal
    ),
    /*h2*/
    titleMedium = TextStyle(
        color = LittleLemonColor.charcoal,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    /*body1*/
    bodyMedium = TextStyle(
        color = LittleLemonColor.green
    ),
    /*body2*/
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        color = LittleLemonColor.green
    ),
    /*button*/
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
)
