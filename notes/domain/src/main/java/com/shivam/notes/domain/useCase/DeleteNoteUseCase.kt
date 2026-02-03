package com.shivam.notes.domain.useCase

import com.shivam.notes.domain.repository.NotesRepository

class DeleteNoteUseCase(private val notesRepository: NotesRepository) {
    operator fun invoke(id: String) = notesRepository.deleteNote(id)
}