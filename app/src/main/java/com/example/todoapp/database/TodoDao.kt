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

    @Query("SELECT * FROM todos WHERE todoTitle LIKE :query")
    fun searchTodo(query: String?): LiveData<List<Todo>>

    @Query("SELECT * FROM todos ORDER BY dateCreated DESC")
    fun sortTodoByCreatedDateNewestFirst(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos ORDER BY dateCreated ASC")
    fun sortTodoByCreatedDateOldestFirst(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos ORDER BY todoTitle ASC")
    fun sortTodoByTitleAZ(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos ORDER BY todoTitle DESC")
    fun sortTodoByTitleZA(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos ORDER BY dateUpdated DESC")
    fun sortTodoByUpdatedDateNewestFirst(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos ORDER BY dateUpdated ASC")
    fun sortTodoByUpdatedDateOldestFirst(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE importantLevel = 1")
    fun filterTodoByImportantLevelLow(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE importantLevel = 2")
    fun filterTodoByImportantLevelMedium(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE importantLevel = 3")
    fun filterTodoByImportantLevelHigh(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE :currentTime - dateCreated < :ONE_DAY_MILLIS")
    fun filterTodoByDayAgo(currentTime: Long, ONE_DAY_MILLIS: Long): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE :currentTime - dateCreated < :ONE_WEEK_MILLIS")
    fun filterTodoByWeekAgo(currentTime: Long, ONE_WEEK_MILLIS: Long): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE :currentTime - dateCreated < :ONE_MONTH_MILLIS")
    fun filterTodoByMonthAgo(currentTime: Long, ONE_MONTH_MILLIS: Long): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE isCompleted == 1")
    fun filterTodoByIsCompleted(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE isCompleted == 0")
    fun filterTodoByIsNotCompleted(): LiveData<List<Todo>>

}