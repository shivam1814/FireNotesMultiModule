package com.shivam.notes.domain.useCase

import com.shivam.notes.domain.repository.NotesRepository

class GetAllNoteUseCase(private val notesRepository: NotesRepository) {
    operator fun invoke(email: String) = notesRepository.getNotes(email)
}