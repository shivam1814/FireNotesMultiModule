package com.shivam.auth.data.di

import com.google.firebase.auth.FirebaseAuth
import com.shivam.auth.data.repository.AuthRepoImpl
import com.shivam.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@InstallIn
@Module
object AuthDiModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    fun provideAuthRepository(auth: FirebaseAuth) : AuthRepository {
        return AuthRepoImpl(auth)
    }

}