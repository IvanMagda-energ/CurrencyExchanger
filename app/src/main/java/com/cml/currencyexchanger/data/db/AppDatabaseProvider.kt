package com.cml.currencyexchanger.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatabaseProvider @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val DB_NAME = "currencyConverter.db"


        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS User (" +
                        "`id` INTEGER PRIMARY KEY NOT NULL DEFAULT 0, " +
                        "`balance` TEXT NOT NULL DEFAULT '{\"bgn\":0.0,\"euro\":1000.0,\"usd\":0.0}', `conversionsAmount` INTEGER NOT NULL DEFAULT 0)")
            }

        }
    }


    private fun buildDb(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
//            .fallbackToDestructiveMigration()
            .addMigrations(
                MIGRATION_1_2
            )
            .build()
    }

    fun provideDb(): AppDatabase {
        return buildDb()
    }
}