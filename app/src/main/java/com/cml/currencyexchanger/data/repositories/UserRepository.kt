package com.cml.currencyexchanger.data.repositories

import android.annotation.SuppressLint
import com.cml.currencyexchanger.data.dao.UserDao
import com.cml.currencyexchanger.data.models.User
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    @SuppressLint("CheckResult")
    fun createDefaultUserIfNotExists(): Maybe<User> {
        return userDao.getUser()
            .subscribeOn(Schedulers.io())
            .switchIfEmpty(
                userDao.clearUserTable()
                    .subscribeOn(Schedulers.io())
                    .andThen(userDao.insertUser(User()))
                    .andThen(userDao.getUser())
            )
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun observeUser(): Observable<User> {
        return userDao.observeUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}