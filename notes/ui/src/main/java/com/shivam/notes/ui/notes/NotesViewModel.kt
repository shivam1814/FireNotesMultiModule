package com.shivam.notes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.auth.domain.model.User
import com.shivam.auth.domain.useCase.GetCurrentUserUseCase
import com.shivam.notes.domain.model.Note
import com.shivam.notes.domain.useCase.CreateNoteUseCase
import com.shivam.notes.domain.useCase.DeleteNoteUseCase
import com.shivam.notes.domain.useCase.GetAllNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getAllNoteUseCase: GetAllNoteUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        observeUserAndNotes()
    }

    private fun observeUserAndNotes() {
        viewModelScope.launch {
            try {
                _user.value = getCurrentUserUseCase()

                val email = _user.value?.email.orEmpty()
                if (email.isBlank()) {
                    _error.value = "User not logged in"
                    return@launch
                }

                getAllNoteUseCase(email)
                    .catch { e ->
                        _error.value = e.message
                    }
                    .collect { notes ->
                        _notes.value = notes
                    }

            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            try {
                deleteNoteUseCase(id)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
