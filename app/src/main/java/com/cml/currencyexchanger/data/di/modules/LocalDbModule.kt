package com.cml.currencyexchanger.data.di.modules

import com.cml.currencyexchanger.data.dao.ExchangeRatesDao
import com.cml.currencyexchanger.data.dao.UserDao
import com.cml.currencyexchanger.data.db.AppDatabase
import com.cml.currencyexchanger.data.db.AppDatabaseProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDbModule {

    @Singleton
    @Provides
    fun provideAppDatabase(provider: AppDatabaseProvider): AppDatabase =
        provider.provideDb()


    @Singleton
    @Provides
    fun provideExchangeRatesDao(db: AppDatabase): ExchangeRatesDao =
        db.getExchangeRatesDao()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao =
        db.getUserDao()
}