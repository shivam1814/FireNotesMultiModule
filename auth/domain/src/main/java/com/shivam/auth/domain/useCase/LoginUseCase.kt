package com.shivam.auth.domain.useCase

import com.shivam.auth.domain.repository.AuthRepository

class LoginUseCase(private var authRepository: AuthRepository) {
    operator fun invoke(email: String, password: String) = authRepository.login(email, password)
}
