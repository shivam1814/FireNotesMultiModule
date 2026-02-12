package com.shivam.notes.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shivam.notes.domain.model.Note

@Composable
fun NoteScreen(modifier: Modifier = Modifier, goToAddEditNoteScreen: () -> Unit) {

    val viewModel = hiltViewModel<NotesViewModel>()
    val notes by viewModel.notes.collectAsStateWithLifecycle()


    NoteScreenContent(
        modifier = modifier,
        notes = notes,
        onDelete = viewModel::deleteNote,
        goToAddEditNoteScreen = goToAddEditNoteScreen
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreenContent(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onDelete: (String) -> Unit,
    goToAddEditNoteScreen:() -> Unit
) {


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Notes") }) },
        contentWindowInsets = WindowInsets(0,0,0,0),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                goToAddEditNoteScreen()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(
                notes.size,
                key = { index -> notes[index].id },
                contentType = { index ->
                    notes[index].id
                }
            ) { index ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .background(
                            if (notes[index].shared) {
                                Color.Green
                            } else {
                                Color.LightGray
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = notes[index].title, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = notes[index].content,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    IconButton(
                        onClick = {
                            onDelete(notes[index].id)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }

                }
            }
        }
    }
}