package com.cml.currencyexchanger.data.db

import androidx.room.TypeConverter
import com.cml.currencyexchanger.App
import com.cml.currencyexchanger.data.models.Balance
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MapStringFloatConverter {

    private val type: Type? = object : TypeToken<Map<String, Float>>() {}.type

    @TypeConverter
    fun toString(listMapStringFloat: Map<String, Float>): String =
        App.get().gson.toJson(listMapStringFloat, type)

    @TypeConverter
    fun toObject(str: String): Map<String, Float> =
        App.get().gson.fromJson(str, type)
}


class BalanceConverter {

    private val type: Type? = object : TypeToken<Balance>() {}.type

    @TypeConverter
    fun toString(balance: Balance): String =
        App.get().gson.toJson(balance, type)

    @TypeConverter
    fun toObject(str: String): Balance =
        App.get().gson.fromJson(str, type)
}
