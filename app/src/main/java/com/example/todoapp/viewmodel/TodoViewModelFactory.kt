package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.repository.TodoRepository
import java.lang.IllegalArgumentException

class TodoViewModelFactory(
    val app: Application,
    private val todoRepository: TodoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(app, todoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}