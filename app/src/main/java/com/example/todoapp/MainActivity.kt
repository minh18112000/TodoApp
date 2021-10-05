package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.viewmodel.TodoViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use view binding in activities
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val todoRepository = TodoRepository(TodoDatabase(this))
        val todoViewModelFactory = TodoViewModelFactory(application, todoRepository)

        todoViewModel = ViewModelProvider(
            this,
            todoViewModelFactory
        ).get(TodoViewModel::class.java)

    }
}