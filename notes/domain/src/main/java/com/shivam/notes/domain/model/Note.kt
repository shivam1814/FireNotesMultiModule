package com.shivam.notes.domain.model

data class Note(
    val id:String,
    val email:String,
    val title:String,
    val content:String,
//    val imageUrl:String,
    val shared: Boolean = false,
    val timeStamp: String = System.currentTimeMillis().toString()
)
