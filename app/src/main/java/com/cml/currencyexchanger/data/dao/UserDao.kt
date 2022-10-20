package com.cml.currencyexchanger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cml.currencyexchanger.data.models.ExchangeRates
import com.cml.currencyexchanger.data.models.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class UserDao {

    @Query("SELECT * FROM User")
    abstract fun getUser(): Single<User>

    @Query("SELECT * FROM User")
    abstract fun observeUser(): Observable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(user: User): Completable

    @Query("DELETE FROM User")
    abstract fun clearUserTable(): Completable
}