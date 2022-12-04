package com.example.tasksapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskItemAdapter(var dao: TaskDao): RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {

    var data = listOf<Task>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        return TaskItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

       holder.taskDone.setOnClickListener {
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



    class TaskItemViewHolder(rootView:CardView): RecyclerView.ViewHolder(rootView) {
        var taskName = rootView.findViewById<TextView>(R.id.task_name)
        var taskDone = rootView.findViewById<CheckBox>(R.id.task_done)

        companion object{
            fun inflateFrom(parent: ViewGroup):TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.task_item,parent,false) as CardView
                return TaskItemViewHolder(view)
            }
        }

        fun bind(item : Task){
            taskName.text = item.taskName
            taskDone.isChecked = item.taskDone
        }
    }


}



