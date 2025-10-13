package com.jesil.calculator.history

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
    var hasExpandedState = remember { false }

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
                when(hasExpandedState) {
                    true -> Box(
                        modifier = Modifier
                            .fillMaxSize(.5f)
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center,
                        content = {
                            NoHistoryDisplay()
                        }
                    )
                    false -> NoHistoryDisplay(modifier.weight(1f))
                }
            }
        )
        LaunchedEffect(sheetState.targetValue.ordinal) {
            when (sheetState.targetValue.ordinal) {
                1 -> hasExpandedState = false
                2 -> hasExpandedState = true
            }
            Log.e(
                "CalculatorHistoryScreen",
                "launched Effect: Launched!!"
            )
        }
    }
//    Log.e(
//        "CalculatorHistoryScreen",
//        "sheetState.hasPartiallyExpandedState: ${sheetState.hasPartiallyExpandedState}"
//    )
    Log.e(
        "CalculatorHistoryScreen",
        "sheetState.The sheet is now expanded: $hasExpandedState"
    )
    Log.e(
        "CalculatorHistoryScreen",
        "sheetState targetValue : ${sheetState.targetValue.ordinal}"
    )
}

@Composable
fun NoHistoryDisplay(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_history),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No History",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    )
}