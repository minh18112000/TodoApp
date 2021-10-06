package com.example.todoapp.fragment

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

class UpdateTodoFragment : Fragment(R.layout.fragment_update_todo) {

    private var _binding: FragmentUpdateTodoBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var currentTodo: Todo
    private lateinit var todoViewModel: TodoViewModel

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

        binding.fabUpdateTodo.setOnClickListener {
            val todoTitle = binding.etTodoTitle.text.toString().trim()

            if(todoTitle.isNotEmpty()) {
                val todo = Todo(currentTodo.id, todoTitle, importantLevel)
                todoViewModel.updateTodo(todo)

                activity?.toast("Todo updated!")

                val direction = UpdateTodoFragmentDirections
                    .actionUpdateTodoFragmentToHomeFragment()
                view?.findNavController()?.navigate(direction)
            } else {
                activity?.toast("Please enter title!")
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_todo_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}