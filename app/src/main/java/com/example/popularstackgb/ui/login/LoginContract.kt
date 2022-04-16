package com.example.popularstackgb.ui.login

import androidx.annotation.MainThread
import com.example.popularstackgb.utils.Publisher
import com.example.popularstackgb.utils.SinglePublisher

interface LoginContract {

    interface ViewModel {
        val shouldShowLoading: Publisher<Boolean>
        val isLoginSuccess: Publisher<Boolean>
        val errorCode: SinglePublisher<Int?>
        val isAddAccountSuccess: SinglePublisher<String>
        val isPasswordReminderSuccess: SinglePublisher<String>

        @MainThread
        fun onLogin(login: String, password: String)

        @MainThread
        fun onForgotPassword(login: String)

        @MainThread
        fun onSignUp(login: String, password: String)
    }
}