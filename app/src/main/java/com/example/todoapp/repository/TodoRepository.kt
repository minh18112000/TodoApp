package com.example.todoapp.repository

import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.model.Todo

class TodoRepository(private val db: TodoDatabase) {

    suspend fun addTodo(todo: Todo) = db.getTodoDao().addTodo(todo)

    suspend fun updateTodo(todo: Todo) = db.getTodoDao().updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) = db.getTodoDao().deleteTodo(todo)

    fun getAllTodos() = db.getTodoDao().getAllTodos()

    fun searchTodo(query: String?) = db.getTodoDao().searchTodo(query)

    fun sortTodoByCreatedDateNewestFirst() = db.getTodoDao().sortTodoByCreatedDateNewestFirst()

    fun sortTodoByCreatedDateOldestFirst() = db.getTodoDao().sortTodoByCreatedDateOldestFirst()

    fun sortTodoByTitleAZ() = db.getTodoDao().sortTodoByTitleAZ()

    fun sortTodoByTitleZA() = db.getTodoDao().sortTodoByTitleZA()

    fun sortTodoByUpdatedDateNewestFirst() = db.getTodoDao().sortTodoByUpdatedDateNewestFirst()

    fun sortTodoByUpdatedDateOldestFirst() = db.getTodoDao().sortTodoByUpdatedDateOldestFirst()

    fun filterTodoByImportantLevelLow() = db.getTodoDao().filterTodoByImportantLevelLow()

    fun filterTodoByImportantLevelMedium() = db.getTodoDao().filterTodoByImportantLevelMedium()

    fun filterTodoByImportantLevelHigh() = db.getTodoDao().filterTodoByImportantLevelHigh()

    fun filterTodoByDayAgo(currentTime: Long, ONE_DAY_MILLIS: Long) =
        db.getTodoDao().filterTodoByDayAgo(currentTime, ONE_DAY_MILLIS)

    fun filterTodoByWeekAgo(currentTime: Long, ONE_WEEK_MILLIS: Long) =
        db.getTodoDao().filterTodoByWeekAgo(currentTime, ONE_WEEK_MILLIS)

    fun filterTodoByMonthAgo(currentTime: Long, ONE_MONTH_MILLIS: Long) =
        db.getTodoDao().filterTodoByMonthAgo(currentTime, ONE_MONTH_MILLIS)

    fun filterTodoByIsCompleted() = db.getTodoDao().filterTodoByIsCompleted()

    fun filterTodoByIsNotCompleted() = db.getTodoDao().filterTodoByIsNotCompleted()
}