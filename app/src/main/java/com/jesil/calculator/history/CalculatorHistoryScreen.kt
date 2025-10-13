package com.jesil.calculator.history

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.jesil.calculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorHistoryScreen(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {
    val history = remember { mutableListOf(emptyList<HistoryModel>()) }
    var hasExpandedState by remember { mutableStateOf(false) }

    val targetFraction by animateFloatAsState(
        targetValue = if (hasExpandedState) 1f else 0.4f,
        animationSpec = if (hasExpandedState) {
            tween(durationMillis = 300, easing = FastOutSlowInEasing)
        } else {
            tween(durationMillis = 300, easing = LinearOutSlowInEasing)
        }
    )

    ModalBottomSheet(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Button(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 20.dp),
                    onClick = onDismiss,
                    content = {
                        Text(text = "Done")
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize(targetFraction)
                        .animateContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    NoHistoryDisplay()
                }
            }
        )
        LaunchedEffect(sheetState.targetValue) {
            hasExpandedState = when (sheetState.targetValue) {
                SheetValue.Expanded -> true
                SheetValue.PartiallyExpanded -> false
                SheetValue.Hidden -> false
            }
        }
    }
}

@Composable
fun NoHistoryDisplay() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_history),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "No History",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    )
}