package com.shivam.notes.domain.repository

import com.shivam.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun createNote(note: Note): Flow<Result<Unit>>

    fun updateNote(note: Note): Flow<Result<Unit>>

    fun deleteNote(id: String): Flow<Result<Unit>>

    fun getNotes(email: String): Flow<List<Note>>

    suspend fun getNote(id: String): Result<Note>

}