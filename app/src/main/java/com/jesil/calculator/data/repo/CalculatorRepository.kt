package com.jesil.calculator.data.repo

import com.jesil.calculator.history.CalculatorHistoryModel
import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {
    suspend fun insertCalculationHistory(calculationHistory: CalculatorHistoryModel)

    fun getAllCalculationHistories(): Flow<Map<String, List<CalculatorHistoryModel>>>

    suspend fun clearAllCalculationHistories()
}