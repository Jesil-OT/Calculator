package com.jesil.calculator.history

data class CalculatorHistoryModel(
    val expression: String,
    val answer: String,
    val date: Long = System.currentTimeMillis(),
)
