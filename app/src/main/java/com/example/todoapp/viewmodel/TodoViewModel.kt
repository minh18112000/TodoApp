package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Todo
import com.example.todoapp.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(
    app: Application,
    private val todoRepository: TodoRepository) : AndroidViewModel(app){

    fun addTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.addTodo(todo)
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.updateTodo(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.deleteTodo(todo)
    }

    fun getAllTodos() = todoRepository.getAllTodos()

    fun searchTodo(query: String?) = todoRepository.searchTodo(query)

    fun sortTodoByCreatedDateNewestFirst() = todoRepository.sortTodoByCreatedDateNewestFirst()

    fun sortTodoByCreatedDateOldestFirst() = todoRepository.sortTodoByCreatedDateOldestFirst()

    fun sortTodoByTitleAZ() = todoRepository.sortTodoByTitleAZ()

    fun sortTodoByTitleZA() = todoRepository.sortTodoByTitleZA()

    fun sortTodoByUpdatedDateNewestFirst() = todoRepository.sortTodoByUpdatedDateNewestFirst()

    fun sortTodoByUpdatedDateOldestFirst() = todoRepository.sortTodoByUpdatedDateOldestFirst()

    fun filterTodoByImportantLevelLow() = todoRepository.filterTodoByImportantLevelLow()

    fun filterTodoByImportantLevelMedium() = todoRepository.filterTodoByImportantLevelMedium()

    fun filterTodoByImportantLevelHigh() = todoRepository.filterTodoByImportantLevelHigh()
}