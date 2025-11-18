package com.jesil.calculator.data.repo

import com.jesil.calculator.data.local.CalculatorDao
import com.jesil.calculator.data.mapper.toEntity
import com.jesil.calculator.data.mapper.toModel
import com.jesil.calculator.history.CalculatorHistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalculatorRepositoryImpl(
    private val calculatorDao: CalculatorDao
): CalculatorRepository {

    override suspend fun insertCalculationHistory(calculationHistory: CalculatorHistoryModel) {
        calculatorDao.insertCalculationHistory(calculationHistory.toEntity())
    }

    override fun getAllCalculationHistories(): Flow<Map<String, List<CalculatorHistoryModel>>> {
        return calculatorDao.getAllCalculationHistories().map { historyEntities ->
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            historyEntities
                .map { it .toModel()} // convert to domain model
                .groupBy { model ->
                    formatter.format(Date(model.date))
                }

        }
    }

    override suspend fun clearAllCalculationHistories() {
        calculatorDao.clearAllCalculationHistories()
    }
}