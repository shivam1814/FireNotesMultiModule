package com.shivam.auth.domain.useCase

import com.shivam.auth.domain.repository.AuthRepository

class LogoutUseCase(private var authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.logout()
}