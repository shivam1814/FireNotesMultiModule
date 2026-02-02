package com.shivam.notes.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.shivam.notes.data.repository.NotesRepoImpl
import com.shivam.notes.domain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotesDataModule {

    @Singleton
    @Provides
    fun provideFirebaseFireStore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    @Provides
    fun provideNotesRepository(firestore: FirebaseFirestore) : NotesRepository {
        return NotesRepoImpl(firestore)
    }

}



