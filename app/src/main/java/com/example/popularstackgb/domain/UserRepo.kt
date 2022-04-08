package com.example.popularstackgb.domain

import com.example.popularstackgb.domain.entities.UserProfile

interface UserRepo {
    fun addUser(user: UserProfile)

    fun getAllUsers(): List<UserProfile>

    fun changeUser(id: String, user: UserProfile)

    fun deleteUser(id: String)
    fun deleteAll()
}

