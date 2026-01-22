package com.shivam.auth.domain.repository

import com.shivam.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(email: String, password: String): Flow<Result<User>>
    fun register(email: String, password: String): Flow<Result<User>>

    suspend fun logout()

    suspend fun getCurrentsUser(): User?

}