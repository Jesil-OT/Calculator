package com.jesil.calculator.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorDisplay(
    modifier: Modifier = Modifier,
    expression: String,
    answer: String
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
        content = {
            Column {
                ExpressionTextField(
                    expression = expression,
                )
            }
        }
    )
}

@Composable
fun ExpressionTextField(
    modifier: Modifier = Modifier,
    expression: String
) {
    val infiniteTransition = rememberInfiniteTransition(label = "expression blinking cursor")
    val blinkColor = infiniteTransition.animateColor(
        initialValue = Color.Transparent,
        targetValue = MaterialTheme.colorScheme.onBackground,
        label = "blinking cursor color",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse
        )
    )
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = expression,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 90.sp
                ),
                maxLines = 1,
                modifier = Modifier.padding(end = 5.dp).animateContentSize()
            )
            //box for cursor
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(90.dp)
                    .background(blinkColor.value),
                contentAlignment = Alignment.CenterEnd,
                content = {}
            )
        }
    )
}

@PreviewLightDark
@Composable
fun CalculatorDisplayPreview() {
    CalculatorTheme {
        CalculatorDisplay(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            expression = "1+2928", answer = "3"
        )
    }
}