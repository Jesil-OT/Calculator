package com.jesil.calculator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context

class CalculatorViewModel : ViewModel() {
    private val _expression = mutableStateOf("")
    val expression: MutableState<String> = _expression

    private val _answer = mutableStateOf("0")
    val answer: MutableState<String> = _answer

    fun buttonClicked(symbol: String) {
        _expression.value.let {

            if(symbol == "+ / -"){
                if (it.startsWith("-")){
                    _expression.value = it.substring(1, it.length)
                    return
                }
                _expression.value = "-$it"
                return
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

            _expression.value = it + symbol

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
        var finalResult =  context.evaluateString(scriptable, equation, "Javascript", 1, null)
        if (finalResult.toString().endsWith(".0")) {
            finalResult = finalResult.toString().replace(".0", "")
        }
        return finalResult.toString()
    }
}