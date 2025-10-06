package com.jesil.calculator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jesil.calculator.action.CalculatorAction
import kotlinx.coroutines.flow.MutableStateFlow
import org.mozilla.javascript.Context

class CalculatorViewModel : ViewModel() {
    private val _expression = MutableStateFlow("")
    val expression: MutableStateFlow<String> = _expression

    private val _answer = MutableStateFlow("")
    val answer: MutableStateFlow<String> = _answer

    fun onCalculatorButtonAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.OnClear -> onClear()
            is CalculatorAction.OnClearAll -> onClearAll()
            is CalculatorAction.OnPlusOrMinus -> onPlusOrMinusClick()
            is CalculatorAction.OnPercent -> {}

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

            is CalculatorAction.OnDecimal -> onNumberClick(".")
            is CalculatorAction.OnEquals -> buttonClicked("=")

        }
    }

    private fun onOperatorClick(newOperator: String) {
        val operators = listOf("+", "-", "×", "/")
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

    private fun onClear(){
        _expression.value.let {
            if (it.isNotEmpty()) {
                _expression.value = it.substring(0, it.length - 1)
            }
        }
    }

    private fun onClearAll(){
        _expression.value = ""
        _answer.value = ""
    }

    private fun onNumberClick(number: String) {
        _expression.value.let {
            _expression.value = it + number
        }
    }

    private fun buttonClicked(symbol: String) {
        _expression.value.let {

            if (symbol == "±") {
                if (it.startsWith("-")) {
                    _expression.value = it.drop(1)
                } else if (it != "0") {
                    _expression.value = "-$it"
                } else {
                    _expression.value = it
                }

//                if (it.startsWith("-")) {
//                    _expression.value = it.substring(1, it.length)
//                    return
//                }
//                _expression.value = "-$it"
//                return

//                if (it.startsWith("-")) {
//                    displayValue = displayValue.drop(1) // remove the minus
//                } else if (displayValue != "0") {
//                    displayValue = "-$displayValue" // add a minus in front
//                }
            }
            if (symbol == "AC") {
                _expression.value = ""
                _answer.value = "0"
                return
            }
            if (symbol == "x") {
                _expression.value += "*"
                return
            }
            if (symbol == "C") {
                if (it.isNotEmpty()) {
                    _expression.value = if (it == "0") "0" else it.substring(0, it.length - 1)
                }
                return
            }
            if (symbol == "=") {
                _expression.value = _answer.value
                return
            }


            try {
                val result = calculateResult(_expression.value)
                _answer.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun calculateResult(equation: String): String {
        val context = Context.enter()
        context.optimizationLevel = -1
        val scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable, equation, "Javascript", 1, null)
        if (finalResult.toString().endsWith(".0")) {
            finalResult = finalResult.toString().replace(".0", "")
        }
        return finalResult.toString()
    }
}