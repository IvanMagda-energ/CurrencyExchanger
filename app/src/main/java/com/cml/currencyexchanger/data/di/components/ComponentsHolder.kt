package com.cml.currencyexchanger.data.di.components

import android.content.Context
import com.cml.currencyexchanger.data.di.components.AppComponent
import com.cml.currencyexchanger.data.di.components.DaggerAppComponent

class ComponentsHolder(ctx: Context) {

    @get:JvmName("appComponent")
    private val appComponent by lazy {
        DaggerAppComponent.factory()
            .create(
                appCtx = ctx
            )
    }

    fun getAppComponent(): AppComponent = appComponent
}