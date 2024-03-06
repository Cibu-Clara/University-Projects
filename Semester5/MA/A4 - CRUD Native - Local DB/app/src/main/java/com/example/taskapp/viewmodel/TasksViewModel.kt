package com.example.taskapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskapp.model.Task
import com.example.taskapp.persistence.TasksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(private val db: TasksDao) : ViewModel() {

    var tasks: LiveData<List<Task>> = MutableLiveData(emptyList())

    init {
        viewModelScope.launch (Dispatchers.IO ){
            tasks = db.getTasks()
        }
    }

    fun createTask(title: String, priorityLevel: String, dateDeadline: String, timeDeadline: String, description: String) {
        val task = Task(title, priorityLevel, dateDeadline, timeDeadline, description)
        viewModelScope.launch(Dispatchers.IO) {
            db.insertTask(task)
        }
    }
    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            db.updateTask(task)
        }
    }

    suspend fun getTask(id: Int) : Task? {
        return db.getTaskById(id)
    }
}

class TaskViewModelFactory(private val db: TasksDao) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return TasksViewModel(
            db = db
        ) as T
    }
}