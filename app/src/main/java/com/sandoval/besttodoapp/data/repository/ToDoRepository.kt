package com.sandoval.besttodoapp.data.repository

import com.sandoval.besttodoapp.data.ToDoDao
import com.sandoval.besttodoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }
}