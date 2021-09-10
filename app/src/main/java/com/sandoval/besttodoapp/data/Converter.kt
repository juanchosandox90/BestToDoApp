package com.sandoval.besttodoapp.data

import androidx.room.TypeConverter
import com.sandoval.besttodoapp.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}