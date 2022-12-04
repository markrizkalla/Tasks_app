package com.example.tasksapp

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel(val dao:TaskDao) : ViewModel() {

    var newTaskName = ""
    val tasks = dao.getAll()



    fun updateTask(task: Task){
        viewModelScope.launch {
            dao.update(task)
        }
    }

    fun addTask(){

        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }
}