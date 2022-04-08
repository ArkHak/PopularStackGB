package com.example.popularstackgb.domain.entities

data class UserProfile(
    val login: String,
    val password: String,
    val email: String = "www.mail@mail.com",
    val avatarUrl: String = "www.avatar@mail.com",
    val age: Int = 25,
)