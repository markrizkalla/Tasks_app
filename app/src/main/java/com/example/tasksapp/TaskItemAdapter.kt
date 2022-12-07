package com.example.tasksapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.TaskItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskItemAdapter(var dao: TaskDao): ListAdapter<Task,TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        return TaskItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

       holder.binding.taskDone.setOnClickListener {
           GlobalScope.launch {
               if (item.taskDone){
                   item.taskDone = false
                   dao.update(item)
               }else{
                   item.taskDone = true
                   dao.update(item)
               }
           }
       }

    }



    class TaskItemViewHolder(val binding: TaskItemBinding): RecyclerView.ViewHolder(binding.root) {


        companion object{
            fun inflateFrom(parent: ViewGroup):TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater,parent,false)
                return TaskItemViewHolder(binding)
            }
        }

        fun bind(item : Task){
            binding.task = item
        }
    }


}



