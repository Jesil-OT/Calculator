package com.jesil.calculator

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jesil.calculator.ui.theme.white
import com.jesil.calculator.ui.theme.yellow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel = viewModel()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = "Calculator") }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .background(yellow),

            ) {
            CalculationOperationBox(
                equation = calculatorViewModel.expression.value,
                answer = calculatorViewModel.answer.value,
                modifier = modifier
                    .weight(1f)
                    .padding(start = 5.dp, end = 5.dp)
            )
            Spacer(modifier = Modifier.padding(top = 30.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(4)
            ) {
                items(
                    items = buttonList,
                ) { buttonNumber ->
                    CalculatorButton(symbol = buttonNumber){
                        calculatorViewModel.buttonClicked(buttonNumber)
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
) {
    val navigateIcon = Icons.Filled.Menu
    val themeIcon = Icons.Outlined.Settings
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(yellow)
    ) {
        Icon(
            navigateIcon,
            contentDescription = "Navigate Icon",
            modifier = Modifier.padding(
                top = 32.dp,
                bottom = 32.dp,
                end = 32.dp,
                start = 5.dp
            )
        )
        Text(
            text = title,
            fontSize = 18.sp,
        )
        Icon(
            themeIcon,
            contentDescription = "Theme Icon",
            modifier = Modifier.padding(
                top = 32.dp,
                bottom = 32.dp,
                start = 32.dp,
                end = 5.dp
            )
        )
    }
}

@Composable
fun CalculationOperationBox(
    equation: String,
    answer: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(white)
    ) {
        Column(modifier = modifier) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                text = equation,
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                text = answer,
                fontSize = 100.sp,
                textAlign = TextAlign.Right
            )
        }
    }
}

@Composable
fun CalculatorButton(symbol: String, onClicked : (String) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(7.dp)
            .padding(bottom = 10.dp)
    ) {
        FloatingActionButton(
            modifier = Modifier.size(85.dp),
            shape = CircleShape,
            containerColor = yellow,
            onClick = { onClicked(symbol) }
        ) {
            Text(
                text = symbol,
                fontSize = symbol.returnSmallTextSize.sp,
                color = getSpecialSymbolsColor(symbol)
            )
        }
    }
}

fun getSpecialSymbolsColor(symbol: String): Color {
    if (symbol == "C" || symbol == "AC" || symbol == "%" || symbol == "x" || symbol == "/"
        || symbol == "+" || symbol == "-" || symbol == "="
    )
        return Color(0xFFFFFFFF)
    return Color(0xFF000000)
}

val String.returnSmallTextSize: Int
    get() {
        if (this == "+ / -") return 14
        if (this == ".") return 40
        return 23
    }


val buttonList = listOf(
    "C", "AC", "%", "+",
    "7", "8", "9", "/",
    "4", "5", "6", "x",
    "1", "2", "3", "-",
    "0", ".", "+ / -", "="
)

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar(title = "Calculator")
}

@Preview
@Composable
fun CalculationOperationBoxPreview() {
    CalculationOperationBox(
        equation = "322/10",
        answer = "32.2"
    )
}

@Preview(device = "id:pixel_5")
@Composable
fun CalculatorPreview(modifier: Modifier = Modifier) {
    CalculatorScreen()
}