package com.cml.currencyexchanger.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey val id: Long = 0,
    val balance: Balance = Balance(),
    val conversionsAmount: Long = 0
)


