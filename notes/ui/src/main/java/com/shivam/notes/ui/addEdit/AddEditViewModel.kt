package com.shivam.notes.ui.addEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.auth.domain.model.User
import com.shivam.auth.domain.useCase.GetCurrentUserUseCase
import com.shivam.notes.domain.model.Note
import com.shivam.notes.domain.useCase.CreateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {


    private val _addEditUiState = MutableStateFlow(AddEditUiState())
    val addEditUiState = _addEditUiState.asStateFlow()


    private val _user = MutableStateFlow<User?>(null)

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _shared = MutableStateFlow(false)
    val shared = _shared.asStateFlow()


    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()


    fun onTitleChange(title: String) {
        _title.update { title }
    }

    fun onContentChange(content: String) {
        _content.update { content }
    }

    fun onSharedChange(shared: Boolean) {
        _shared.update { shared }
    }


    init {
        viewModelScope.launch {
            _user.update {
                getCurrentUserUseCase()
            }
        }
    }

    fun createNote() {

        val note = Note(
            id = UUID.randomUUID().toString(),
            email = _user.value?.email.orEmpty(),
            title = title.value,
            content = content.value,
            shared = shared.value,
        )

        createNoteUseCase(note)
            .onStart {
                _addEditUiState.update { AddEditUiState(isLoading = true) }
            }
            .onEach { result ->
                result.onSuccess { data ->
                    _addEditUiState.update {
                        AddEditUiState(
                            isLoading = false,
                            isPopBackStack = true
                        )
                    }

                }.onFailure { error ->

                }
            }
            .onCompletion {
                _addEditUiState.update { AddEditUiState() }
            }
            .launchIn(viewModelScope)

    }


}