package com.jesil.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jesil.calculator.components.CalculatorDisplay
import com.jesil.calculator.components.CalculatorKeypad
import com.jesil.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            CalculatorDisplay(
                expression = "1 + 2",
                answer = "3",
                modifier = Modifier.weight(.6f)
            )
            CalculatorKeypad(
                calculatorActions = {},
                modifier = Modifier.weight(1f)
            )
        }
    )
}

@PreviewLightDark
@Composable
fun CalculatorScreenPreview() = CalculatorTheme {
    CalculatorScreen()
}
