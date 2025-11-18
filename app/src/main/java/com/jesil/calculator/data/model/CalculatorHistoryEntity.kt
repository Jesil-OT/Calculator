package com.jesil.calculator.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculator_history")

data class CalculatorHistoryEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "calculation_expression") val expression: String,

    @ColumnInfo(name = "calculation_answer") val answer: String,

    @ColumnInfo(name = "calculation_date") val date: Long = System.currentTimeMillis(),
)
