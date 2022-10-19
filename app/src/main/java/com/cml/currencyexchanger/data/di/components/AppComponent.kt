package com.cml.currencyexchanger.data.di.components

import android.content.Context
import com.cml.currencyexchanger.data.di.modules.AppModule
import com.cml.currencyexchanger.view.viewmodels.CurrencyConverterViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appCtx: Context,
        ): AppComponent
    }

    fun getCurrencyConverterViewModel(): CurrencyConverterViewModel.Factory
}