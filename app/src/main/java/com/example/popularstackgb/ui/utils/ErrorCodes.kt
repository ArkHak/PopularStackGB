package com.example.popularstackgb.ui.utils

import com.example.popularstackgb.R


enum class ErrorCodes(val codeError: Int) {
    NO_ERRORS(R.string.no_error),
    ERROR_INVALID_LOGIN_OR_PASSWORD(R.string.error_invalid_login_or_password),
    SERVER_IS_NOT_AVAILABLE(R.string.server_is_not_available),
    EMPTY_FIELDS(R.string.error_empty_fields),
    USER_DOES_NOT_EXIST(R.string.error_user_does_not_exist),
    FILL_FIELDS(R.string.error_fill_fields),
    USER_ALREADY_EXISTS(R.string.error_user_already_exist),
    NO_FOUND(R.string.error_no_found),
}