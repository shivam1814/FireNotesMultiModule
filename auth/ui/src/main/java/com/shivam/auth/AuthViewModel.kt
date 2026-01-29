package com.shivam.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.auth.domain.useCase.LoginUseCase
import com.shivam.auth.domain.useCase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private var _isLogin = MutableStateFlow(true)
    val isLogin = _isLogin.asStateFlow()


    fun onEmailChange(email: String) {
        _email.update { email }
    }

    fun onPasswordChange(password: String) {
        _password.update { password }
    }

    fun onToggleChange() {
        _isLogin.update { it.not() }
    }

    fun login() {
        loginUseCase(_email.value, _password.value).onEach { result ->
            result.onSuccess {

            }.onFailure {

            }

        }.launchIn(viewModelScope)
    }

    fun register() {
        registerUseCase(_email.value, _password.value).onEach { result ->
            result.onSuccess {

            }.onFailure {

            }
        }.launchIn(viewModelScope)
    }


}