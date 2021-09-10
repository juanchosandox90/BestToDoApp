package com.sandoval.besttodoapp.ui.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import com.sandoval.besttodoapp.data.models.Priority

//This viewModelclass will be shared with the whole project so thats why is been isolated
class SharedViewModel(application: Application) : AndroidViewModel(application) {

    fun verifyDataFromUserInput(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW
            else -> Priority.LOW
        }
    }

}