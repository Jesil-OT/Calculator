package com.jesil.calculator

import androidx.room.Room
import com.jesil.calculator.data.local.CalculatorDao
import com.jesil.calculator.data.local.CalculatorDatabase
import com.jesil.calculator.data.repo.CalculatorRepository
import com.jesil.calculator.data.repo.CalculatorRepositoryImpl
import com.jesil.calculator.history.CalculatorHistoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<CalculatorDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CalculatorDatabase::class.java,
            "calculator_db"
        ).fallbackToDestructiveMigration(false)
            .build()
    }


    single<CalculatorDao> {
        get<CalculatorDatabase>().calculatorDao()
    }
    single<CalculatorRepository> {
        CalculatorRepositoryImpl(calculatorDao = get<CalculatorDao>())
    }
    viewModelOf(::CalculatorViewModel) bind CalculatorViewModel::class
    viewModelOf(::CalculatorHistoryViewModel) bind CalculatorHistoryViewModel::class
}