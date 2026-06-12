package com.jesil.calculator

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CalculationApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalculationApplication)
            androidLogger()
            modules(appModule)
        }
    }
}