package com.sandoval.besttodoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sandoval.besttodoapp.data.ToDoDatabase
import com.sandoval.besttodoapp.data.models.ToDoData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()

    //TODO: For best practices this should be accesed using the repository pattern.

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoDao.insertData(
                toDoData
            )
        }
    }
}