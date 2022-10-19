package com.cml.currencyexchanger.data.di.modules

import com.cml.currencyexchanger.data.api.ExchangeRatesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RemoteApiModule {

    @Singleton
    @Provides
    fun provideExchangeRatesApi(retrofit: Retrofit) = retrofit.create(ExchangeRatesApi::class.java)
}