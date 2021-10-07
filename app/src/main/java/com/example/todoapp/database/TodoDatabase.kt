package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.model.Todo

// declare the entities for the database and set the version number
@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun getTodoDao(): TodoDao

    // The companion object allows clients to access the methods for
    // creating or getting the database without instantiating the class.
    // Since the only purpose of this class is to provide a database,
    // there is no reason to ever instantiate it.
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var instance: TodoDatabase? = null
        private val LOCK = Any()

        // if the instance is not null, then return it,
        // if it is, then create the database

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "todo_db"
        ).fallbackToDestructiveMigration()
            .build()

    }

}