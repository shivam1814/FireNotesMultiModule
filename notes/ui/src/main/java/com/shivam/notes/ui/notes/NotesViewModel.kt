package com.shivam.notes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.auth.domain.model.User
import com.shivam.auth.domain.useCase.GetCurrentUserUseCase
import com.shivam.notes.domain.useCase.CreateNoteUseCase
import com.shivam.notes.domain.useCase.GetAllNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val getAllNoteUseCase: GetAllNoteUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)

    init {
        viewModelScope.launch {

        }
    }

}