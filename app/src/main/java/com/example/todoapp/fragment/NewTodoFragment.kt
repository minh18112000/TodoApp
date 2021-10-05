package com.example.todoapp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentNewTodoBinding

class NewTodoFragment : Fragment(R.layout.fragment_new_todo) {

    private var _binding: FragmentNewTodoBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewTodoBinding.inflate(
            inflater,
            container,
            false
        )



        setHasOptionsMenu(true)

        return binding.root
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