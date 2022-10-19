package com.cml.currencyexchanger.data.db

import androidx.room.TypeConverter
import com.cml.currencyexchanger.App
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListMapStringFloatConverter {

    private val type: Type? = object : TypeToken<List<Map<String, Float>>>() {}.type

    @TypeConverter
    fun toString(listMapStringFloat: List<Map<String, Float>>): String =
        App.get().gson.toJson(listMapStringFloat, type)

    @TypeConverter
    fun toObject(str: String): List<Map<String, Float>> =
        App.get().gson.fromJson(str, type)
}