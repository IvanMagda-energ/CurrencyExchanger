package com.cml.currencyexchanger.data.db

import androidx.room.TypeConverter
import com.cml.currencyexchanger.App
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