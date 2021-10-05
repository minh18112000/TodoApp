package com.example.todoapp.repository

import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.model.Todo

class TodoRepository(private val db: TodoDatabase) {

    suspend fun addTodo(todo: Todo) = db.getTodoDao().addTodo(todo)

    suspend fun updateTodo(todo: Todo) = db.getTodoDao().updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) = db.getTodoDao().deleteTodo(todo)
}