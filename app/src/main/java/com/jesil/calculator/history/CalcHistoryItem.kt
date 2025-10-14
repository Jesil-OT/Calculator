package com.jesil.calculator.history

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.calculator.ui.theme.CalculatorTheme

@Composable
fun CalcHistoryItem(
    modifier: Modifier = Modifier,
    expression: String,
    answer: String,
) {
    Row(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            BoxWithBorder(text = expression)
            Spacer(Modifier.width(20.dp))
            Text(
                text = "=",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(Modifier.width(20.dp))
            BoxWithBorder(text = answer)
        }
    ) 
}

@Composable
fun BoxWithBorder(
    text: String
) {
    Box(
        modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            shape = RoundedCornerShape(15.dp)
        ),
        contentAlignment = Alignment.Center,
        content = {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 7.dp),
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
            )
        }
    )
}


@PreviewLightDark
@Composable
private fun CalcHistoryItemPreview() = CalculatorTheme {
    CalcHistoryItem(
        modifier = Modifier.fillMaxWidth(),
        expression = "2+2",
        answer = "4",
    )
}