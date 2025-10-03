package com.jesil.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.calculator.ui.theme.Black
import com.jesil.calculator.ui.theme.CalculatorTheme
import com.jesil.calculator.ui.theme.DarkGray
import com.jesil.calculator.ui.theme.LightGray

@Composable
fun CalcButton(
    modifier: Modifier = Modifier,
    symbol: String,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSystemInDarkTheme()) DarkGray else LightGray
    val borderColor = if (isSystemInDarkTheme()) Black else DarkGray
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .border(
                width = 2.dp,
                color = borderColor
            )
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center,
        content = {
            Text(
                text = symbol,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp
                ),
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun CalcButtonPreview() {
    CalculatorTheme {
        CalcButton(symbol = "1", onClick = {})
    }
}