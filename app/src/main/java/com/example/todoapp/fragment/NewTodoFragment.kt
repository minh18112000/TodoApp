package com.example.todoapp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentNewTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.toast
import com.example.todoapp.viewmodel.TodoViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*


class NewTodoFragment : Fragment(R.layout.fragment_new_todo) {

    // create binding object
    private var _binding: FragmentNewTodoBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var mView: View
    private var importantLevel: Int = 1
    private var dueDate = 0L
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // inflate the Fragment's view
        // (which is equivalent to using setContentView() for an Activity)
        _binding = FragmentNewTodoBinding.inflate(
            inflater,
            container,
            false
        )

        todoViewModel = (activity as MainActivity).todoViewModel

        binding.imgImportantLevelOne.setOnClickListener {
            binding.imgImportantLevelOne.setImageResource(R.drawable.ic_done)
            binding.imgImportantLevelTwo.setImageResource(0)
            binding.imgImportantLevelThree.setImageResource(0)
            importantLevel = 1
        }

        binding.imgImportantLevelTwo.setOnClickListener {
            binding.imgImportantLevelTwo.setImageResource(R.drawable.ic_done)
            binding.imgImportantLevelOne.setImageResource(0)
            binding.imgImportantLevelThree.setImageResource(0)
            importantLevel = 2
        }

        binding.imgImportantLevelThree.setOnClickListener {
            binding.imgImportantLevelThree.setImageResource(R.drawable.ic_done)
            binding.imgImportantLevelTwo.setImageResource(0)
            binding.imgImportantLevelOne.setImageResource(0)
            importantLevel = 3
        }

        var count = 0
        binding.todayCalendarButton.setOnClickListener {
            if (count % 2 == 0) {
                binding.calendarGroup.visibility = View.VISIBLE
                count = 1
            } else {
                binding.calendarGroup.visibility = View.GONE
                count = 0
            }
        }

        val calendar = Calendar.getInstance()

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.clear()
            calendar.set(year, month, dayOfMonth)
            dueDate = calendar.timeInMillis
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun saveTodo(view: View) {
        val todoTitle = binding.etTodoTitle.text.toString().trim()
        val todoImportantLevel = importantLevel
        val todoDateCreated = System.currentTimeMillis()


        if (todoTitle.isNotEmpty()) {
            // create new item
            val todo = Todo(
                0,
                todoTitle,
                todoImportantLevel,
                todoDateCreated,
                todoDateCreated,
                false,
                dueDate
            )
            todoViewModel.addTodo(todo)
            Snackbar.make(
                view,
                "Todo saved successfully",
                Snackbar.LENGTH_SHORT
            ).show()

            view.findNavController()
                .navigate(NewTodoFragmentDirections.actionNewTodoFragmentToHomeFragment())
        } else {
            activity?.toast("Please enter todo title!")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu -> {
                saveTodo(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_todo_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}