package com.example.todoapp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentUpdateTodoBinding

class UpdateTodoFragment : Fragment(R.layout.fragment_update_todo) {

    private var _binding: FragmentUpdateTodoBinding? = null
    private val binding
        get() = _binding!!

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