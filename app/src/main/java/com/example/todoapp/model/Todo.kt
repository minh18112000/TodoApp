package com.example.todoapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todos")
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val todoTitle: String,
    val importantLevel: Int
) : Parcelable