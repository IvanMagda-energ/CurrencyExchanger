package com.cml.currencyexchanger.data.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREF_FILE_NAME = "AppPreferences"
        const val PREF_EXCHANGE_RATE_BASE = "exchangeRateBase"
        const val PREF_EXCHANGE_RATE_RATES = "exchangeRateRates"
    }
}