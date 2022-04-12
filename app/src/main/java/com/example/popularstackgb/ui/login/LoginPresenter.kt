package com.example.popularstackgb.ui.login

import com.example.popularstackgb.domain.LoginUsecase
import com.example.popularstackgb.ui.utils.ErrorCodes

class LoginPresenter(private val loginUsecase: LoginUsecase) : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private var isSuccess: Boolean = false
    private var codeError: Int = ErrorCodes.NO_ERRORS.codeError
    private var showError: Boolean = false

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        if (isSuccess) {
            view.setSuccess()
        } else {
            if (showError)
                view.setError(codeError)
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
                    showError(ErrorCodes.ERROR_INVALID_LOGIN_OR_PASSWORD.codeError)
                    isSuccess = false
                }
            }
        } else {
            showError(ErrorCodes.SERVER_IS_NOT_AVAILABLE.codeError)
        }
    }

    override fun onForgotPassword(login: String) {
        if (loginUsecase.checkConnect()) {
            if (login.isBlank()) {
                showError(ErrorCodes.EMPTY_FIELDS.codeError)
            } else {
                if (loginUsecase.checkAccount(login)) {
                    view?.showLoading()
                    loginUsecase.forgotPassword(login) { password ->
                        view?.hideLoading()
                        view?.passwordReminderSuccess(password)
                    }
                } else {
                    showError(ErrorCodes.USER_DOES_NOT_EXIST.codeError)
                }
            }
        } else {
            showError(ErrorCodes.SERVER_IS_NOT_AVAILABLE.codeError)
        }
    }

    override fun onSignUp(login: String, password: String) {
        if (loginUsecase.checkConnect()) {
            if (login.isBlank() || password.isBlank()) {
                showError(ErrorCodes.FILL_FIELDS.codeError)
            } else {
                view?.showLoading()
                if (loginUsecase.checkAccount(login)) {
                    view?.hideLoading()
                    showError(ErrorCodes.USER_ALREADY_EXISTS.codeError)
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
            showError(ErrorCodes.SERVER_IS_NOT_AVAILABLE.codeError)
        }
    }

    private fun showError(codeError: Int) {
        showError = true
        this.codeError = codeError
        view?.setError(this.codeError)
    }
}