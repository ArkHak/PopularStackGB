package com.example.popularstackgb.utils

enum class ErrorCodes(val textError: String) {
    NO_ERRORS(""),
    ERROR_INVALID_LOGIN_OR_PASSWORD("Invalid username or password"),
    SERVER_IS_NOT_AVAILABLE("No connection to server"),
    EMPTY_FIELDS("Enter your login in the username field"),
    USER_DOES_NOT_EXIST("This user does not exist"),
    FILL_FIELDS("Fill in the username and password fields"),
    USER_ALREADY_EXISTS("A user with the same name already exists"),
    NO_FOUND("No found"),
}