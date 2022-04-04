package com.example.popularstackgb

class LoginModel : LoginContract.Model {

    //username to password
    private val accounts = mutableMapOf(
        "admin" to "admin",
        "user" to "12345678",
    )

    override fun checkConnect(): Boolean {
        val luckyNumber = (0 until 10).random()
        return luckyNumber <= 5
    }

    override fun checkCredentials(login: String, password: String): Boolean {
        for ((key, value) in accounts) {
            if (key == login && value == password) {
                return true
            }
        }
        return false
    }

    override fun checkAccount(login: String): Boolean {
        return accounts.containsKey(login)
    }

    override fun passwordReminder(login: String): String {
        return accounts[login].toString()
    }

    override fun addAccount(login: String, password: String) {
        accounts[login] = password
    }
}