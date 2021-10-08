package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
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
import java.util.concurrent.TimeUnit

private val ONE_DAY_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var _binding: TodoLayoutAdapterBinding? = null
    private val binding
        get() = _binding!!

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
        _binding =
            TodoLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TodoViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = differ.currentList[position]
        val formatDateCreated = SimpleDateFormat("MMM dd HH:mm")
        val formatDueDate = SimpleDateFormat("EEE, MMM dd")

        // trans type long of date to "dd/MM hh:mm"
        val todoDateUpdated = Date(currentTodo.dateUpdated)
        val todoDueDate = Date(currentTodo.dueDate)

        // View item on RecyclerView
        val title = holder.itemBinding.tvTodoTitle
        val importantLevel = holder.itemBinding.imgImportantLevel
        val dateCreated = holder.itemBinding.tvDateCreated
        val dueDate = holder.itemBinding.todoRowChip

        title.text = currentTodo.todoTitle

        if (currentTodo.isCompleted) {
            // set strikethrough for textView
            title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            title.setTypeface(null, Typeface.NORMAL)
            title.setTypeface(null, Typeface.ITALIC)
        }

        if (currentTodo.importantLevel == 1) {
            importantLevel.setImageResource(R.drawable.yellow_dot)
        } else if (currentTodo.importantLevel == 2) {
            importantLevel.setImageResource(R.drawable.blue_dot)
        } else {
            importantLevel.setImageResource(R.drawable.red_dot)
        }

        dateCreated.text = formatDateCreated.format(todoDateUpdated)

        if (currentTodo.dueDate != 0L) {
            dueDate.text = formatDueDate.format(todoDueDate)
            if (currentTodo.dueDate + ONE_DAY_MILLIS < System.currentTimeMillis()) {
                dueDate.setTextColor(Color.parseColor("#FF0000"))
            }
        } else {
            dueDate.text = "No due"
        }

        holder.itemView.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToUpdateTodoFragment(currentTodo)
            it.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}