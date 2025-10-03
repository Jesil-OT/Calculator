package com.jesil.calculator.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jesil.calculator.action.CalculatorAction
import com.jesil.calculator.ui.theme.CalculatorTheme

@Composable
fun ButtonKeypadLayout(
    modifier: Modifier = Modifier,
    calculatorActions: (CalculatorAction) -> Unit
) {

}

@Preview
@Composable
private fun ButtonKeypadLayoutPreview() {
    CalculatorTheme {
        ButtonKeypadLayout(
            calculatorActions = {}
        )
    }
}