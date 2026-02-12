package com.shivam.notes.ui.addEdit

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddEditScreen(modifier: Modifier = Modifier, popBackStack: () -> Unit) {

    val viewModel = hiltViewModel<AddEditViewModel>()

    val title by viewModel.title.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()
    val shared by viewModel.shared.collectAsStateWithLifecycle()

    val uiState by viewModel.addEditUiState.collectAsStateWithLifecycle()


    LaunchedEffect(uiState) {
        if(uiState.isPopBackStack) {
            popBackStack()
        }
    }

    AddEditScreenContent(
        modifier = modifier.fillMaxSize(),
        title = title,
        content = content,
        shared = shared,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onSharedChange = viewModel::onSharedChange,
        createNote = viewModel::createNote
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreenContent(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    shared: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSharedChange: (Boolean) -> Unit,
    createNote: () -> Unit
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Add / Edit") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                createNote
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Title")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Enter Note Title") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Content")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Enter Note Content") }
            )


            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Shared")
                Spacer(modifier = Modifier.width(8.dp))
                Checkbox(checked = shared, onCheckedChange = onSharedChange)
            }


        }
    }


}