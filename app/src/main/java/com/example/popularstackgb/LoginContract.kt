package com.example.popularstackgb

import android.os.Handler
import androidx.annotation.MainThread

class LoginContract {

    interface View {
        @MainThread
        fun setSuccess()

        @MainThread
        fun setError(error: String)

        @MainThread
        fun showLoading()

        @MainThread
        fun hideLoading()

        @MainThread
        fun getHandler(): Handler

        fun passwordReminderSuccess(password: String)
        fun addAccountSuccess(login: String)
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onForgotPassword(login: String)
        fun onSignUp(login: String, password: String)
    }

    interface Model {
        fun checkConnect(): Boolean
        fun checkCredentials(login: String, password: String): Boolean
        fun checkAccount(login: String): Boolean
        fun passwordReminder(login: String): String
        fun addAccount(login: String, password: String)
    }
}