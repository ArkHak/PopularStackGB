package com.example.popularstackgb.data.loginusecase

import androidx.annotation.MainThread
import com.example.popularstackgb.domain.LoginApi
import com.example.popularstackgb.domain.LoginUsecase

class LoginUsecaseImpl(
    private val api: LoginApi
) : LoginUsecase {
    override fun login(
        username: String,
        password: String,
        @MainThread callback: (Boolean) -> Unit
    ) {
        Thread {
            val result = api.login(username, password)
            callback(result)
        }.start()
    }

    override fun forgotPassword(username: String, callback: (String) -> Unit) {
        Thread {
            val password = api.forgotPassword(username)
            callback(password)
        }.start()
    }

    override fun addAccount(username: String, password: String, callback: (Boolean) -> Unit) {
        Thread {
            val result = api.register(username, password)
            callback(result)
        }.start()
    }

    override fun checkConnect(): Boolean {
        val luckyNumber = (0 until 10).random()
        return luckyNumber <= 5
    }

    override fun checkAccount(login: String): Boolean {
        return api.checkAccount(login)
    }
}