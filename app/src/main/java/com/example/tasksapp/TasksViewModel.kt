package com.example.tasksapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel(val dao:TaskDao) : ViewModel() {

    var newTaskName = ""
    val tasks = dao.getAll()


    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask : LiveData<Long?>
      get() = _navigateToTask

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

    fun onTaskClicked(taskId:Long){
        _navigateToTask.value = taskId
    }

    fun onTaskNavigated(){
        _navigateToTask.value = null
    }


}