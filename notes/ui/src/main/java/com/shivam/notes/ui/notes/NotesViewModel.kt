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
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        viewModelScope.launch {
            _user.update {
                getCurrentUserUseCase()
            }
        }
        getNotes()
    }

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()


    fun getNotes() {
        getAllNoteUseCase(_user.value?.email.orEmpty())
            .onEach { result ->
                _notes.update { result }
            }.launchIn(viewModelScope)
    }

    fun deleteNote(id: String) {
        deleteNoteUseCase(id).onEach { result ->
            result.onSuccess {

                }
                .onFailure {

                }
        }.launchIn(viewModelScope)
    }

}