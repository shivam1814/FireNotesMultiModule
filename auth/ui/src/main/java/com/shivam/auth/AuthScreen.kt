package com.shivam.auth

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AuthScreen(modifier: Modifier = Modifier , navigateToNoteNavGraph: () -> Unit) {

    val viewModel = hiltViewModel<AuthViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isLogin by viewModel.isLogin.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if(uiState.navigateToNoteNavGraph) {
            navigateToNoteNavGraph()
        }
    }

    AuthScreenContent(
        modifier = modifier.fillMaxSize(),
        isLoading = isLoading,
        isLogin = isLogin,
        onToggleChange = viewModel::onToggleChange,
        email = email,
        onEmailChange = viewModel::onEmailChange,
        password = password,
        onPasswordChange = viewModel::onPasswordChange,
        onLogin = viewModel::login,
        onRegister = viewModel::register
    )

}

@Composable
fun AuthScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isLogin: Boolean,
    onToggleChange: () -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {

        Text(
            text = "FireNotes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Text(text = "Email")
        Spacer(Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text(text = "Password")
        Spacer(Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))


        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (isLogin) {
                Button(
                    onClick = onLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                ) {
                    Text(text = "SignIn")
                }
                Text(
                    text = "Create an account?",
                    modifier = Modifier
                        .clickable {
                            onToggleChange()
                        }
                )
            } else {
                Button(
                    onClick = onRegister,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                ) {
                    Text(text = "SignUp")
                }
                Text(
                    text = "Already have an account? Login",
                    modifier = Modifier
                        .clickable {
                            onToggleChange()
                        }
                )
            }
        }


    }

}