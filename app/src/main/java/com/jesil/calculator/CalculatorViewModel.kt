package com.jesil.calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.calculator.action.CalculatorAction
import com.jesil.calculator.data.local.CalculatorDao
import com.jesil.calculator.data.repo.CalculatorRepository
import com.jesil.calculator.history.CalculatorHistoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mozilla.javascript.Context

const val TAG = "CalculatorViewModel"
class CalculatorViewModel(

    private val calculatorRepository: CalculatorRepository
) : ViewModel() {
    private val _expression = MutableStateFlow("")
    val expression: MutableStateFlow<String> = _expression

    val answer = _expression.map {
        calculateResult(it)
    }.stateIn(
        scope = viewModelScope,
        started =  SharingStarted.Eagerly,
        initialValue = ""
    )

    fun onCalculatorButtonAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.OnClear -> onClear()
            is CalculatorAction.OnClearAll -> onClearAll()
            is CalculatorAction.OnPlusOrMinus -> onPlusOrMinusClick()
            is CalculatorAction.OnPercent -> onOperatorClick("%")

            is CalculatorAction.OnAdd -> onOperatorClick("+")
            is CalculatorAction.OnSubtract -> onOperatorClick("-")
            is CalculatorAction.OnMultiply -> onOperatorClick("×")
            is CalculatorAction.OnDivide -> onOperatorClick("/")

            is CalculatorAction.OnZero -> onNumberClick("0")
            is CalculatorAction.OnOne -> onNumberClick("1")
            is CalculatorAction.OnTwo -> onNumberClick("2")
            is CalculatorAction.OnThree -> onNumberClick("3")
            is CalculatorAction.OnFour -> onNumberClick("4")
            is CalculatorAction.OnFive -> onNumberClick("5")
            is CalculatorAction.OnSix -> onNumberClick("6")
            is CalculatorAction.OnSeven -> onNumberClick("7")
            is CalculatorAction.OnEight -> onNumberClick("8")
            is CalculatorAction.OnNine -> onNumberClick("9")

            is CalculatorAction.OnDecimal -> onDecimalClicked()
            is CalculatorAction.OnEquals -> onEqualToClicked()
        }
    }

    private fun onOperatorClick(newOperator: String) {
        val operators = listOf("+", "-", "×", "/", "%")
        val current = _expression.value

        if (current.isEmpty()) return

        _expression.value = when {
            operators.any { current.endsWith(it) } -> {
                // Replace the last operator
                current.dropLast(1) + newOperator
            }

            else -> {
                // Append a new one
                current + newOperator
            }
        }
    }

    private fun onDecimalClicked(){
        val equation = _expression.value
        // Split the expression by operators to get the last number
        val lastNumber = equation.split("+", "-", "×", "*", "/").lastOrNull() ?: ""

        when {
            equation.isEmpty() -> {
                // Case 1: nothing yet — start with "0."
                _expression.value = "0."
            }
            lastNumber.contains(".") -> {
                // Case 2: current number already has a dot — ignore
                return
            }
            else -> {
                // Case 3: safe to add dot
                _expression.value = "$equation."
            }
        }
    }

    private fun onPlusOrMinusClick() {
        _expression.value.let {
            if (it.startsWith("-")) {
                _expression.value = it.drop(1)
            } else if (it != "0") {
                _expression.value = "-$it"
            } else {
                _expression.value = it
            }
        }
    }

    private fun onClear() {
        _expression.value.let {
            if (it.isNotEmpty()) {
                _expression.value = it.substring(0, it.length - 1)
            }
        }
    }

    private fun onClearAll() {
        _expression.value = ""
    }

    private fun onNumberClick(number: String) {
        _expression.value.let {
            _expression.value = it + number
        }
    }

    private fun onEqualToClicked(){
        viewModelScope.launch {
            calculatorRepository.insertCalculationHistory(
                CalculatorHistoryModel(
                    expression = _expression.value,
                    answer = answer.value
                )
            )
            Log.d(TAG, "onEqualToClicked: insert complete: ${CalculatorHistoryModel(
                expression = _expression.value,
                answer = answer.value
            )}")
        }
        _expression.value = calculateResult(_expression.value)
    }

    private fun calculateResult(equation: String): String {
        // Clean up the equation first — remove trailing operators
        val cleanedEquation = equation
            .replace("×", "*")
            .replace("%", "/100")
            .trimEnd('+', '-', '*', '/', '.')

        // If empty after trimming, return empty string
        if (cleanedEquation.isEmpty()) return ""

        return try {
            val context = Context.enter()
            context.optimizationLevel = -1 // required for Android
            val scope = context.initStandardObjects()

            val result = context.evaluateString(scope, cleanedEquation, "JavaScript", 1, null)

            // Check for undefined results
            var output = if (result != Context.getUndefinedValue()) result.toString() else ""

            // Remove ".0" if it's a whole number
            if (output.endsWith(".0")) {
                output = output.dropLast(2)
            }

            output
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        } finally {
            Context.exit()
        }
    }

}