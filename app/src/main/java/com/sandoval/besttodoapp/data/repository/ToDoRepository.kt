package com.sandoval.besttodoapp.data.repository

import androidx.lifecycle.LiveData
import com.sandoval.besttodoapp.data.ToDoDao
import com.sandoval.besttodoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteSingleItem(toDoData: ToDoData) {
        toDoDao.deleteSingleItem(toDoData)
    }

    suspend fun deleteAllItems() {
        toDoDao.deleteAllItems()
    }

    fun searchToDoDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchToDoDatabase(searchQuery)
    }
}