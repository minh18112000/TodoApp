package com.example.todoapp.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentUpdateTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.toast
import com.example.todoapp.viewmodel.TodoViewModel
import java.util.*

class UpdateTodoFragment : Fragment(R.layout.fragment_update_todo) {

    private var _binding: FragmentUpdateTodoBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var currentTodo: Todo
    private lateinit var todoViewModel: TodoViewModel
    private var dueDate = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentUpdateTodoBinding.inflate(
            inflater,
            container,
            false
        )

        // get the viewModel
        todoViewModel = (activity as MainActivity).todoViewModel

        // extract the argument from the bundle]
        val args = UpdateTodoFragmentArgs.fromBundle(requireArguments())
        currentTodo = args.todo!!

        // set title and body of item is updated
        binding.etTodoTitle.setText(currentTodo.todoTitle)
        if (currentTodo.isCompleted) {
            binding.cbComplete.isChecked = true
        }
        var importantLevel: Int = currentTodo.importantLevel

        if (importantLevel == 1) {
            binding.imgImportantLevelOne.setImageResource(R.drawable.ic_done)
        } else if (importantLevel == 2) {
            binding.imgImportantLevelTwo.setImageResource(R.drawable.ic_done)
        } else {
            binding.imgImportantLevelThree.setImageResource(R.drawable.ic_done)
        }

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
            if(count%2 == 0) {
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

        binding.fabUpdateTodo.setOnClickListener {
            val todoTitle = binding.etTodoTitle.text.toString().trim()
            val dateCreated = currentTodo.dateCreated
            val dateUpdated = System.currentTimeMillis()
            val isCompleted = binding.cbComplete.isChecked

            if (todoTitle.isNotEmpty()) {
                val todo = Todo(
                    currentTodo.id,
                    todoTitle,
                    importantLevel,
                    dateCreated,
                    dateUpdated,
                    isCompleted,
                    dueDate
                )
                todoViewModel.updateTodo(todo)

                activity?.toast("Todo updated!")

                val direction = UpdateTodoFragmentDirections
                    .actionUpdateTodoFragmentToHomeFragment()
                view?.findNavController()?.navigate(direction)
            } else {
                activity?.toast("Please enter title!")
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    // create share Intent
    private fun getShareIntent(): Intent {
        val args = UpdateTodoFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                "Todo shared: ${args.todo?.todoTitle}, ${args.todo?.importantLevel}"
            )
        return shareIntent
    }

    // starting an activity with new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    private fun deleteTodo() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Todo")
            setMessage("Are you sure want to permanently delete this todo?")
            setPositiveButton("DELETE") { _, _ ->
                todoViewModel.deleteTodo(currentTodo)

                view?.findNavController()?.navigate(
                    UpdateTodoFragmentDirections.actionUpdateTodoFragmentToHomeFragment()
                )
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_todo_menu, menu)

        // showing the Share Menu Item dynamically
        if (getShareIntent().resolveActivity(requireActivity().packageManager) == null) {
            menu.findItem(R.id.share_menu).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_menu -> deleteTodo()
            R.id.share_menu -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}