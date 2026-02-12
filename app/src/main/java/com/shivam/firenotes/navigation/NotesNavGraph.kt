package com.shivam.firenotes.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shivam.notes.ui.addEdit.AddEditScreen
import com.shivam.notes.ui.notes.NoteScreen
import kotlinx.serialization.Serializable

object NotesNavGraph : BaseNavGraph {

    sealed interface Dest {
        @Serializable
        data object Root : Dest

        @Serializable
        data object Notes : Dest

        @Serializable
        data object AddEdit : Dest
    }

    override fun build(
        modifier: Modifier,
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<Dest.Root>(startDestination = Dest.Notes) {

            composable<Dest.Notes> {
                NoteScreen(
                    modifier,
                    goToAddEditNoteScreen = {
                        navController.navigate(Dest.AddEdit)
                    }
                )
            }

            composable<Dest.AddEdit> {
                AddEditScreen(modifier, popBackStack = { navController.popBackStack() })
            }

        }
    }
}