package com.jesil.calculator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesil.calculator.data.model.CalculatorHistoryEntity

@Database(
    entities = [CalculatorHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CalculatorDatabase: RoomDatabase()  {
    abstract fun calculatorDao(): CalculatorDao
}