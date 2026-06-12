package com.jesil.calculator.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.calculator.data.repo.CalculatorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val TAG = "CalculatorHistoryViewModel"
class CalculatorHistoryViewModel(
    private val calculatorRepository: CalculatorRepository
): ViewModel(){
    private val _history = MutableStateFlow<Map<String, List<CalculatorHistoryModel>>>(mapOf())
    val history: StateFlow<Map<String, List<CalculatorHistoryModel>>> = _history.asStateFlow()

    init {
        viewModelScope.launch {
            calculatorRepository.getAllCalculationHistories().collect { calculationHistory ->
                _history.value = calculationHistory
            }
        }
    }


    fun getFormattedDateLabel(rawDate: String): String {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        val yesterdayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(yesterday.time)

        return when (rawDate) {
            today -> "Today"
            yesterdayStr -> "Yesterday"
            else -> {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val date = inputFormat.parse(rawDate)
                if (date != null) {
                    outputFormat.format(date)
                } else {
                    rawDate
                }
            }
        }
    }
}