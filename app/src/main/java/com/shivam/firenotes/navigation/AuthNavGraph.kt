package com.shivam.firenotes.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shivam.auth.AuthScreen
import kotlinx.serialization.Serializable

object AuthNavGraph : BaseNavGraph {


    sealed interface Dest {
        @Serializable
        data object Root : Dest

        @Serializable
        data object Auth : Dest
    }

    override fun build(
        modifier: Modifier,
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<Dest.Root>(
            startDestination = Dest.Auth
        ) {
            composable<Dest.Auth> {
                AuthScreen(modifier, navigateToNoteNavGraph = {
                    navController.navigate(NotesNavGraph.Dest.Notes) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                })
            }

        }
    }
}