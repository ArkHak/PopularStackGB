package com.example.popularstackgb.data.userrepo

import com.example.popularstackgb.domain.UserRepo
import com.example.popularstackgb.domain.entities.UserProfile

class MockUserRepoImpl : UserRepo {

    override fun addUser(user: UserProfile) {
        TODO("Not yet implemented")
    }

    override fun getAllUsers(): List<UserProfile> {
        TODO("Not yet implemented")
    }

    override fun changeUser(id: String, user: UserProfile) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}