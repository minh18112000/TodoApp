package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.model.Todo

/*
 A data access object (DAO) to map Kotlin functions to SQL queries
 When you use a Room database, you query the database by defining and
 calling Kotlin functions in your code. These Kotlin functions map to
 SQL queries. You define those mappings in a DAO using annotations, and
 Room creates the necessary code.
*/
@Dao
interface TodoDao {

    // it triggers whenever you try to insert an object that has the same @PrimaryKey
    // as an object of the same type that is already saved on the database. REPLACE
    // will overwrite the item in the database with the new item.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getAllTodos(): LiveData<List<Todo>>
}