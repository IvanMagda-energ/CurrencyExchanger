package com.cml.currencyexchanger.data.db

import android.content.Context
import androidx.room.Room
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatabaseProvider @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val DB_NAME = "currencyConverter.db"
    }


    fun provideDb(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
//            .fallbackToDestructiveMigration()
            .build()
    }
}