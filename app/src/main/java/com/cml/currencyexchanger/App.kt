package com.cml.currencyexchanger

import android.app.Application
import com.cml.currencyexchanger.data.di.components.ComponentsHolder

class App : Application() {

    private lateinit var componentsHolder: ComponentsHolder

    companion object {
        private lateinit var sInstance: App
        fun get() = sInstance
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        initDagger()
    }

    private fun initDagger() {
        componentsHolder = ComponentsHolder(this)
    }

    fun components() = componentsHolder


}