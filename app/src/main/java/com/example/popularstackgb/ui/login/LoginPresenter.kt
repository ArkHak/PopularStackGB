package com.example.popularstackgb.ui.login

import com.example.popularstackgb.domain.LoginUsecase

class LoginPresenter(private val loginUsecase: LoginUsecase) : LoginContract.Presenter {
    private var view: LoginContract.View? = null
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
        if (loginUsecase.checkConnect()) {
            view?.showLoading()
            loginUsecase.login(login, password) { result ->
                view?.hideLoading()
                if (result) {
                    view?.setSuccess()
                    isSuccess = true
                } else {
                    showError(ERROR_INVALID_LOGIN_OR_PASSWORD)
                    isSuccess = false
                }
            }
        } else {
            showError(SERVER_IS_NOT_AVAILABLE)
        }
    }

    override fun onForgotPassword(login: String) {
        if (loginUsecase.checkConnect()) {
            if (login.isBlank()) {
                showError(EMPTY_FIELDS)
            } else {
                if (loginUsecase.checkAccount(login)) {
                    view?.showLoading()
                    loginUsecase.forgotPassword(login) { password ->
                        view?.hideLoading()
                        view?.passwordReminderSuccess(password)
                    }
                } else {
                    showError(USER_DOES_NOT_EXIST)
                }
            }
        } else {
            showError(SERVER_IS_NOT_AVAILABLE)
        }
    }

    override fun onSignUp(login: String, password: String) {
        if (loginUsecase.checkConnect()) {
            if (login.isBlank() || password.isBlank()) {
                showError(FILL_FIELDS)
            } else {
                view?.showLoading()
                if (loginUsecase.checkAccount(login)) {
                    view?.hideLoading()
                    showError(USER_ALREADY_EXISTS)
                } else {
                    loginUsecase.addAccount(login, password) { result ->
                        view?.hideLoading()
                        if (result) {
                            view?.addAccountSuccess(login)
                        }
                    }

                }
            }
        } else {
            showError(SERVER_IS_NOT_AVAILABLE)
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
        private const val USER_DOES_NOT_EXIST = "This user does not exist"
        private const val FILL_FIELDS = "Fill in the username and password fields"
        private const val USER_ALREADY_EXISTS = "A user with the same name already exists"
        private const val NOT_ERROR = ""
    }
}