package com.example.popularstackgb.ui.login

import com.example.popularstackgb.domain.LoginUsecase
import com.example.popularstackgb.ui.utils.ErrorCodes
import com.example.popularstackgb.utils.Publisher
import com.example.popularstackgb.utils.SinglePublisher

class LoginViewModel(
    private val loginUsecase: LoginUsecase
) : LoginContract.ViewModel {
    override val shouldShowLoading: Publisher<Boolean> = Publisher()
    override val isLoginSuccess: Publisher<Boolean> = Publisher()
    override val errorCode: SinglePublisher<Int?> = SinglePublisher()
    override val isAddAccountSuccess: SinglePublisher<String> = SinglePublisher()
    override val isPasswordReminderSuccess: SinglePublisher<String> = SinglePublisher()

    override fun onLogin(login: String, password: String) {
        if (loginUsecase.checkConnect()) {
            shouldShowLoading.post(true)
            loginUsecase.login(login, password) { result ->
                shouldShowLoading.post(false)
                if (result) {
                    isLoginSuccess.post(true)
                    errorCode.post(ErrorCodes.NO_ERRORS.codeError)
                } else {
                    isLoginSuccess.post(false)
                    errorCode.post(ErrorCodes.ERROR_INVALID_LOGIN_OR_PASSWORD.codeError)
                }
            }
        } else {
            isLoginSuccess.post(false)
            errorCode.post(ErrorCodes.SERVER_IS_NOT_AVAILABLE.codeError)
        }
    }

    override fun onForgotPassword(login: String) {
        if (loginUsecase.checkConnect()) {
            if (login.isBlank()) {
                errorCode.post(ErrorCodes.EMPTY_FIELDS.codeError)
            } else {
                if (loginUsecase.checkAccount(login)) {
                    shouldShowLoading.post(true)
                    loginUsecase.forgotPassword(login) { password ->
                        shouldShowLoading.post(false)
                        isPasswordReminderSuccess.post(password)
                    }
                } else {
                    errorCode.post(ErrorCodes.USER_DOES_NOT_EXIST.codeError)
                }
            }
        } else {
            errorCode.post(ErrorCodes.SERVER_IS_NOT_AVAILABLE.codeError)
        }
    }

    override fun onSignUp(login: String, password: String) {
        if (loginUsecase.checkConnect()) {
            if (login.isBlank() || password.isBlank()) {
                errorCode.post(ErrorCodes.FILL_FIELDS.codeError)
            } else {
                shouldShowLoading.post(true)
                if (loginUsecase.checkAccount(login)) {
                    shouldShowLoading.post(false)
                    errorCode.post(ErrorCodes.USER_ALREADY_EXISTS.codeError)
                } else {
                    loginUsecase.addAccount(login, password) { result ->
                        shouldShowLoading.post(false)
                        if (result) {
                            isAddAccountSuccess.post(login)
                        }
                    }

                }
            }
        } else {
            errorCode.post(ErrorCodes.SERVER_IS_NOT_AVAILABLE.codeError)
        }
    }
}