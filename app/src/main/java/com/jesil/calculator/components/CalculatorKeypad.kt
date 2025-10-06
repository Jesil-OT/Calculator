package com.jesil.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jesil.calculator.action.CalculatorAction
import com.jesil.calculator.ui.theme.CalculatorTheme
import com.jesil.calculator.ui.theme.DarkGray
import com.jesil.calculator.ui.theme.LargoTeal
import com.jesil.calculator.ui.theme.LightGray
import com.jesil.calculator.ui.theme.OtherLargoTeal

@Composable
fun CalculatorKeypad(
    modifier: Modifier = Modifier,
    calculatorActions: (CalculatorAction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        content = {
            FirstRow(
                modifier = Modifier.weight(1f),
                onClearClick = { calculatorActions(CalculatorAction.OnClear) },
                onPlusOrMinusClick = { calculatorActions(CalculatorAction.OnPlusOrMinus) },
                onPercentClick = { calculatorActions(CalculatorAction.OnPercent) },
                onAddClick = { calculatorActions(CalculatorAction.OnAdd) }
            )
            SecondRow(
                modifier = Modifier.weight(1f),
                onOneClick = { calculatorActions(CalculatorAction.OnOne) },
                onTwoClick = { calculatorActions(CalculatorAction.OnTwo) },
                onThreeClick = { calculatorActions(CalculatorAction.OnThree) },
                onSubtractClick = { calculatorActions(CalculatorAction.OnSubtract) }
            )
            ThirdRow(
                modifier = Modifier.weight(1f),
                onFourClick = { calculatorActions(CalculatorAction.OnFour) },
                onFiveClick = { calculatorActions(CalculatorAction.OnFive) },
                onSixClick = { calculatorActions(CalculatorAction.OnSix) },
                onMultiplyClick = { calculatorActions(CalculatorAction.OnMultiply) }
            )
            FourthRow(
                modifier = Modifier.weight(1f),
                onSevenClick = { calculatorActions(CalculatorAction.OnSeven) },
                onEightClick = { calculatorActions(CalculatorAction.OnEight) },
                onNineClick = { calculatorActions(CalculatorAction.OnNine) },
                onDivideClick = { calculatorActions(CalculatorAction.OnDivide) }
            )
            FifthRow(
                modifier = Modifier.weight(1f),
                onZeroClick = { calculatorActions(CalculatorAction.OnZero) },
                onDecimalClick = { calculatorActions(CalculatorAction.OnDecimal) },
                onEqualsClick = { calculatorActions(CalculatorAction.OnEquals) }
            )
        }
    )
}

@Composable
fun FirstRow(
    modifier: Modifier = Modifier,
    onClearClick: () -> Unit,
    onPlusOrMinusClick: () -> Unit,
    onPercentClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val backgroundColor = if (isSystemInDarkTheme()) DarkGray else LightGray
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        content = {
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "C",
                onClick = onClearClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "±",
                onClick = onPlusOrMinusClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "%",
                onClick = onPercentClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier
                    .background(Color.Black)
                    .weight(1f),
                symbol = "+",
                onClick = onAddClick,
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    )
}

@Composable
fun SecondRow(
    modifier: Modifier = Modifier,
    onOneClick: () -> Unit,
    onTwoClick: () -> Unit,
    onThreeClick: () -> Unit,
    onSubtractClick: () -> Unit
) {
    val backgroundColor = if (isSystemInDarkTheme()) DarkGray else LightGray

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        content = {
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "1",
                onClick = onOneClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "2",
                onClick = onTwoClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "3",
                onClick = onThreeClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "-",
                onClick = onSubtractClick,
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    )
}

@Composable
fun ThirdRow(
    modifier: Modifier = Modifier,
    onFourClick: () -> Unit,
    onFiveClick: () -> Unit,
    onSixClick: () -> Unit,
    onMultiplyClick: () -> Unit
) {
    val backgroundColor = if (isSystemInDarkTheme()) DarkGray else LightGray
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        content = {
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "4",
                onClick = onFourClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "5",
                onClick = onFiveClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "6",
                onClick = onSixClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "×",
                onClick = onMultiplyClick,
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    )
}

@Composable
fun FourthRow(
    modifier: Modifier = Modifier,
    onSevenClick: () -> Unit,
    onEightClick: () -> Unit,
    onNineClick: () -> Unit,
    onDivideClick: () -> Unit
) {
    val backgroundColor = if (isSystemInDarkTheme()) DarkGray else LightGray
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        content = {
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "7",
                onClick = onSevenClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "8",
                onClick = onEightClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "9",
                onClick = onNineClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "/",
                onClick = onDivideClick,
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    )
}

@Composable
fun FifthRow(
    modifier: Modifier = Modifier,
    onZeroClick: () -> Unit,
    onDecimalClick: () -> Unit,
    onEqualsClick: () -> Unit
) {
    val backgroundColor = if (isSystemInDarkTheme()) DarkGray else LightGray
    val actionColor = if (isSystemInDarkTheme()) LargoTeal else OtherLargoTeal
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        content = {
            CalcButton(
                modifier = Modifier.weight(.5f),
                symbol = "0",
                onClick = onZeroClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(.5f),
                symbol = "•",
                onClick = onDecimalClick,
                backgroundColor = backgroundColor
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                symbol = "=",
                onClick = onEqualsClick,
                backgroundColor = actionColor
            )
        }
    )
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@PreviewLightDark
@Composable
private fun CalculatorKeypadPreview() {
    CalculatorTheme {
        CalculatorKeypad(
            calculatorActions = {}
        )
    }
}