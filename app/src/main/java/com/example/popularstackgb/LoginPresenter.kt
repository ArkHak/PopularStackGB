package com.example.popularstackgb

import java.lang.Thread.sleep

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val model = LoginModel()
    private var isSuccess: Boolean = false
    private var errorText: String = NOT_ERROR
    private var showError: Boolean = false

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        if (isSuccess) {
            view.setSuccess()
        } else {
            if (showError)
                view.setError(errorText)
        }
    }

    override fun onLogin(login: String, password: String) {
        if (model.checkConnect()) {
            view?.showLoading()
            Thread {
                sleep(3_000)
                view?.getHandler()?.post {
                    view?.hideLoading()
                    if (model.checkCredentials(login, password)) {
                        view?.setSuccess()
                        isSuccess = true
                    } else {
                        showError(ERROR_INVALID_LOGIN_OR_PASSWORD)
                        isSuccess = false
                    }
                }
            }.start()
        } else {
            showError(SERVER_IS_NOT_AVAILABLE)
        }
    }

    override fun onForgotPassword(login: String) {
        if (login.isBlank()) {
            showError(EMPTY_FIELDS)
        } else {
            if (model.checkAccount(login)) {
                view?.passwordReminderSuccess(model.passwordReminder(login))
            } else {
                showError(USER_DOES_NOT_EXIST)
            }
        }
    }

    override fun onSignUp(login: String, password: String) {
        if (login.isBlank() || password.isBlank()) {
            showError(FILL_FIELDS)
        } else {
            if (model.checkAccount(login)) {
                showError(USER_ALREADY_EXISTS)
            } else {
                model.addAccount(login, password)
                view?.addAccountSuccess(login)
            }
        }
    }

    private fun showError(error: String) {
        showError = true
        errorText = error
        view?.setError(errorText)
    }

    companion object {
        private const val ERROR_INVALID_LOGIN_OR_PASSWORD = "Invalid username or password"
        private const val SERVER_IS_NOT_AVAILABLE = "No connection to server"
        private const val EMPTY_FIELDS = "Enter your login in the username field"
        private const val USER_DOES_NOT_EXIST = "Enter your login in the username field"
        private const val FILL_FIELDS = "Fill in the username and password fields"
        private const val USER_ALREADY_EXISTS = "A user with the same name already exists"
        private const val NOT_ERROR = ""
    }
}