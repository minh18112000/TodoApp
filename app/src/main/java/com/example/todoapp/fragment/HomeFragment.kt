package com.example.todoapp.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.TodoViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        todoViewModel = (activity as MainActivity).todoViewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        binding.fabAddTodo.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToNewTodoFragment())
        }
    }

    private fun setUpRecyclerView() {
        todoAdapter = TodoAdapter()

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )

            setHasFixedSize(true)
            adapter = todoAdapter
        }

        activity?.let {
            todoViewModel.getAllTodos().observe(viewLifecycleOwner, { todos ->
                todoAdapter.differ.submitList(todos)
                updateUi(todos)
            })
        }
    }

    private fun updateUi(todos: List<Todo>) {
        if (todos.isNotEmpty()) {
            binding.cardView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.cardView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchTodo(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchTodo(newText)
        }
        return true
    }

    private fun searchTodo(query: String?) {
        val searchQuery = "%$query%"
        todoViewModel.searchTodo(searchQuery).observe(this, { list ->
            todoAdapter.differ.submitList(list)
        })
    }

    private fun sortTodoByCreatedDateNewestFirst() {
        todoViewModel.sortTodoByCreatedDateNewestFirst()
            .observe(viewLifecycleOwner, { todo ->
                todoAdapter.differ.submitList(todo)
            })
    }

    private fun sortTodoByCreatedDateOldestFirst() {
        todoViewModel.sortTodoByCreatedDateOldestFirst()
            .observe(viewLifecycleOwner, { todo ->
                todoAdapter.differ.submitList(todo)
            })
    }

    private fun sortTodoByTitleAZ() {
        todoViewModel.sortTodoByTitleAZ()
            .observe(viewLifecycleOwner, { todo ->
                todoAdapter.differ.submitList(todo)
            })
    }

    private fun sortTodoByTitleZA() {
        todoViewModel.sortTodoByTitleZA()
            .observe(viewLifecycleOwner, { todo ->
                todoAdapter.differ.submitList(todo)
            })
    }

    private fun sortTodoByUpdatedDateNewestFirst() {
        todoViewModel.sortTodoByUpdatedDateNewestFirst()
            .observe(viewLifecycleOwner, { todo ->
                todoAdapter.differ.submitList(todo)
            })
    }

    private fun sortTodoByUpdatedDateOldestFirst() {
        todoViewModel.sortTodoByUpdatedDateOldestFirst()
            .observe(viewLifecycleOwner, { todo ->
                todoAdapter.differ.submitList(todo)
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_created_date_newest_first -> sortTodoByCreatedDateNewestFirst()
            R.id.sort_by_created_date_oldest_first -> sortTodoByCreatedDateOldestFirst()
            R.id.sort_by_title_az -> sortTodoByTitleAZ()
            R.id.sort_by_title_za -> sortTodoByTitleZA()
            R.id.sort_by_updated_date_newest_first -> sortTodoByUpdatedDateNewestFirst()
            R.id.sort_by_updated_date_oldest_first -> sortTodoByUpdatedDateOldestFirst()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
