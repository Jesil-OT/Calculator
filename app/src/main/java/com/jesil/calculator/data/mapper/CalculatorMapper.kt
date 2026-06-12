package com.jesil.calculator.data.mapper

import com.jesil.calculator.data.model.CalculatorHistoryEntity
import com.jesil.calculator.history.CalculatorHistoryModel

fun CalculatorHistoryModel.toEntity(): CalculatorHistoryEntity{
    return CalculatorHistoryEntity(
        expression = expression,
        answer = answer,
        date = date,
    )
}

fun CalculatorHistoryEntity.toModel(): CalculatorHistoryModel{
    return CalculatorHistoryModel(
        expression = expression,
        answer = answer,
        date = date
    )
}
