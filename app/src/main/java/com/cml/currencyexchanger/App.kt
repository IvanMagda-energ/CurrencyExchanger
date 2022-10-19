package com.cml.currencyexchanger

import android.app.Application
import com.cml.currencyexchanger.data.di.components.ComponentsHolder
import com.google.gson.Gson
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var gson: Gson
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
        componentsHolder.getAppComponent().inject(this)
    }

    fun components() = componentsHolder


}