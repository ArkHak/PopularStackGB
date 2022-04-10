package com.example.popularstackgb.domain

import androidx.annotation.MainThread

interface LoginUsecase {

    fun login(
        username: String,
        password: String,
        @MainThread callback: (Boolean) -> Unit
    )

    fun forgotPassword(
        username: String,
        @MainThread callback: (String) -> Unit
    )

    fun addAccount(
        username: String,
        password: String,
        @MainThread callback: (Boolean) -> Unit
    )

    fun checkConnect(): Boolean
    fun checkAccount(login: String): Boolean
}