package com.cml.currencyexchanger.data.repositories

import android.annotation.SuppressLint
import com.cml.currencyexchanger.data.dao.UserDao
import com.cml.currencyexchanger.data.models.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    @SuppressLint("CheckResult")
    fun createDefaultUserIfNotExists(): Completable {
        return userDao.getUser()
            .subscribeOn(Schedulers.io())
            .doOnError {
                userDao.clearUserTable()
                    .subscribeOn(Schedulers.io())
                    .andThen(userDao.insertUser(User()))
            }
            .ignoreElement()
    }

    fun observeUser(): Observable<User> {
        return userDao.observeUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }




}