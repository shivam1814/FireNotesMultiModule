package com.shivam.firenotes.di

import com.shivam.notes.domain.repository.NotesRepository
import com.shivam.notes.domain.useCase.CreateNoteUseCase
import com.shivam.notes.domain.useCase.DeleteNoteUseCase
import com.shivam.notes.domain.useCase.GetAllNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NoteDomainModule {

    @Provides
    fun provideCreateNoteUseCase(notesRepository: NotesRepository): CreateNoteUseCase {
        return CreateNoteUseCase(notesRepository)
    }

    @Provides
    fun provideDeleteNoteUseCase(notesRepository: NotesRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(notesRepository)
    }

    @Provides
    fun provideGetAllNoteUseCase(notesRepository: NotesRepository): GetAllNoteUseCase {
        return GetAllNoteUseCase(notesRepository)
    }

}