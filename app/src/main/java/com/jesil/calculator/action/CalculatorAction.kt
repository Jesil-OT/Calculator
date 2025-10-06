package com.jesil.calculator.action

sealed interface CalculatorAction {
    data object OnClear: CalculatorAction
    data object OnClearAll: CalculatorAction
    data object OnPlusOrMinus: CalculatorAction
    data object OnPercent: CalculatorAction
    data object OnAdd: CalculatorAction
    data object OnOne: CalculatorAction
    data object OnTwo: CalculatorAction
    data object OnThree: CalculatorAction
    data object OnSubtract: CalculatorAction
    data object OnFour: CalculatorAction
    data object OnFive: CalculatorAction
    data object OnSix: CalculatorAction
    data object OnMultiply: CalculatorAction
    data object OnSeven: CalculatorAction
    data object OnEight: CalculatorAction
    data object OnNine: CalculatorAction
    data object OnDivide: CalculatorAction
    data object OnZero: CalculatorAction
    data object OnDecimal: CalculatorAction
    data object OnEquals: CalculatorAction
}