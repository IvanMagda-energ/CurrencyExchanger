package com.cml.currencyexchanger.data.di.components

import android.content.Context
import com.cml.currencyexchanger.App
import com.cml.currencyexchanger.data.di.modules.DataModule
import com.cml.currencyexchanger.data.di.modules.RemoteApiModule
import com.cml.currencyexchanger.data.di.modules.RemoteModule
import com.cml.currencyexchanger.view.viewmodels.CurrencyConverterViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RemoteModule::class, DataModule::class, RemoteApiModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appCtx: Context,
        ): AppComponent
    }

    fun getCurrencyConverterViewModel(): CurrencyConverterViewModel.Factory

    fun inject(app: App)
}