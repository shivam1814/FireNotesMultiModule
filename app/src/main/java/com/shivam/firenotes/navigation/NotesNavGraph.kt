package com.shivam.firenotes.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

object NotesNavGraph: BaseNavGraph {

    sealed interface Dest{
        @Serializable
        data object Root: Dest

        @Serializable
        data object Notes: Dest

        @Serializable
        data class AddEdit(val id: String?):Dest
    }

    override fun build(
        modifier: Modifier,
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<Dest.Root>(startDestination = Dest.Root) {

        }
    }
}