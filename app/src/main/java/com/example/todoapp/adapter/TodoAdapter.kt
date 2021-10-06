package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoLayoutAdapterBinding
import com.example.todoapp.fragment.HomeFragmentDirections
import com.example.todoapp.model.Todo
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(val itemBinding: TodoLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // get current item
        val currentTodo = differ.currentList[position]
        val sdf = SimpleDateFormat("dd/MM hh:mm")
        val todoDateCreated = Date(currentTodo.dateCreated)

        // View item on RecyclerView
        holder.itemBinding.tvTodoTitle.text = currentTodo.todoTitle
        if (currentTodo.importantLevel == 1) {
            holder.itemBinding.imgImportantLevel.setImageResource(R.drawable.yellow_dot)
        } else if (currentTodo.importantLevel == 2) {
            holder.itemBinding.imgImportantLevel.setImageResource(R.drawable.blue_dot)
        } else {
            holder.itemBinding.imgImportantLevel.setImageResource(R.drawable.red_dot)
        }
        holder.itemBinding.tvNoteDateCreated.text = sdf.format(todoDateCreated)
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateTodoFragment(currentTodo)
            it.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}