package com.jesil.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jesil.calculator.action.CalculatorAction
import com.jesil.calculator.components.CalculatorDisplay
import com.jesil.calculator.components.CalculatorKeypad
import com.jesil.calculator.history.CalculatorHistoryScreen
import com.jesil.calculator.ui.theme.CalculatorTheme
import com.jesil.calculator.ui.theme.LargoTeal
import com.jesil.calculator.ui.theme.OtherLargoTeal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    val iconTint = if (isSystemInDarkTheme()) LargoTeal else OtherLargoTeal
    val viewModel: CalculatorViewModel = viewModel()
    val expression by viewModel.expression.collectAsState()
    val answer by viewModel.answer.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        content = {
                            Icon(
                                tint = iconTint,
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            showBottomSheet = true
                        },
                        content = {
                            Icon(
                                tint = iconTint,
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_history),
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        },
        content = { innerPadding ->
            CalculatorInnerScreen(
                modifier = Modifier.padding(innerPadding),
                expression = expression,
                answer = answer,
                onCalculatorButtonAction = viewModel::onCalculatorButtonAction
            )
            if (showBottomSheet) {
                CalculatorHistoryScreen(
                    onDismiss = {
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                    sheetState = sheetState
                )
            }
        }
    )
}


@Composable
fun CalculatorInnerScreen(
    modifier: Modifier = Modifier,
    expression: String,
    answer: String,
    onCalculatorButtonAction: (CalculatorAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            CalculatorDisplay(
                expression = expression,
                answer = answer,
                modifier = Modifier
                    .weight(.5f)
                    .padding(horizontal = 16.dp, vertical = 32.dp)
            )
            CalculatorKeypad(
                calculatorActions = onCalculatorButtonAction,
                modifier = Modifier.weight(1f)
            )
        }
    )
}

@PreviewLightDark
@Composable
fun CalculatorScreenPreview() = CalculatorTheme {
    CalculatorInnerScreen(
        expression = "",
        answer = "",
        onCalculatorButtonAction = {}
    )
}
