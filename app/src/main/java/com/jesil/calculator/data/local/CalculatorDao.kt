package com.jesil.calculator.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.jesil.calculator.data.model.CalculatorHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculatorDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCalculationHistory(calculationHistory: CalculatorHistoryEntity)

    @Query("SELECT * FROM calculator_history ORDER BY calculation_date DESC")
    fun getAllCalculationHistories(): Flow<List<CalculatorHistoryEntity>>

    @Query("DELETE FROM calculator_history")
    suspend fun clearAllCalculationHistories()
}