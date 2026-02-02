package com.shivam.notes.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.shivam.notes.domain.model.Note
import com.shivam.notes.domain.repository.NotesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NotesRepoImpl(
    private val firestore: FirebaseFirestore,
//    private val firebaseStorage: FirebaseStorage
) : NotesRepository {

    companion object {
        private const val NOTES = "notes"
    }

    private val notesCollection by lazy {
        firestore.collection(NOTES)
    }

    override fun createNote(note: Note): Flow<Result<Unit>> {
        return callbackFlow {
            try {
                notesCollection.add(note).await()
                trySend(Result.success(Unit))
            } catch (e: Exception) {
                trySend(Result.failure(e))
            }

            awaitClose { }
        }
    }

    override fun updateNote(
        note: Note
    ): Flow<Result<Unit>> {
        TODO("Not yet implemented")
    }

    override fun deleteNote(id: String): Flow<Result<Unit>> {
        return callbackFlow {

            val querySnapshot = notesCollection.whereEqualTo("id", id)
                .limit(1)
                .get()
                .await()

            querySnapshot.documents.forEach { document ->
                document.reference.delete().await()
            }

            awaitClose { }
        }
    }

    override fun getNotes(email: String): Flow<List<Note>> {
        return callbackFlow {
            notesCollection.whereEqualTo("email", email)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }

                    val notes = value?.toObjects(Note::class.java)
                    if (notes != null) {
                        trySend(notes)
                    }

                }
        }
    }

    override suspend fun getNote(id: String): Result<Note> {
        TODO("Not yet implemented")
    }
}