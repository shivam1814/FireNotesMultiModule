package com.shivam.notes.domain.useCase

import com.shivam.notes.domain.model.Note
import com.shivam.notes.domain.repository.NotesRepository

class CreateNoteUseCase(private val notesRepository: NotesRepository) {
    operator fun invoke(note: Note) = notesRepository.createNote(note)
}