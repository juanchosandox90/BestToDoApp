package com.sandoval.besttodoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sandoval.besttodoapp.utils.databaseTable

@Entity(tableName = databaseTable)
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val priority: Priority,
    val description: String
)
