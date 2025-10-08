package com.jesil.calculator.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.calculator.ui.theme.Black
import com.jesil.calculator.ui.theme.CalculatorTheme
import com.jesil.calculator.ui.theme.DarkGray
import com.jesil.calculator.ui.theme.LightGray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalcButton(
    modifier: Modifier = Modifier,
    symbol: String,
    backgroundColor: Color,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textFontSize: TextUnit = 25.sp,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    val borderColor = if (isSystemInDarkTheme()) Black else DarkGray.copy(alpha = .2f)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .border(
                width = .5.dp,
                color = borderColor
            )
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = borderColor)
            ),
        contentAlignment = Alignment.Center,
        content = {
            Text(
                text = symbol,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = textColor,
                    fontSize = textFontSize
                ),
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun CalcButtonPreview() {
    CalculatorTheme {
        val backgroundColor = if (isSystemInDarkTheme()) DarkGray else LightGray

        CalcButton(symbol = "1", onClick = {}, backgroundColor = backgroundColor)
    }
}