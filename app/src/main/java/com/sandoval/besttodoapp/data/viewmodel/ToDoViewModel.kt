package com.sandoval.besttodoapp.data.viewmodel

import android.app.Application
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.ToDoDatabase
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.data.repository.ToDoRepository
import com.sandoval.besttodoapp.ui.fragments.adapters.ListAdapter
import com.sandoval.besttodoapp.ui.fragments.view.SimpleCustomSnackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val toDoRepository: ToDoRepository = ToDoRepository(toDoDao)
    val getAllData: LiveData<List<ToDoData>> = toDoRepository.getAllData

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.updateData(toDoData)
        }
    }

    fun deleteSingleItem(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteSingleItem(toDoData)
        }
    }

    fun deleteAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteAllItems()
        }
    }

    fun restoreDeleteItem(
        view: View,
        deletedItem: ToDoData,
        adapterPosition: Int,
        listAdapter: ListAdapter
    ) {
        val clickListener: View.OnClickListener = View.OnClickListener {
            insertData(deletedItem)
            listAdapter.notifyItemChanged(adapterPosition)
        }

        val customSnackbar = SimpleCustomSnackbar.make(
            view,
            "Deleted '${deletedItem.title}'. Do you want to restore it?",
            4000,
            clickListener,
            R.drawable.ic_baseline_undo_24,
            "UNDO",
            ContextCompat.getColor(getApplication(), R.color.nightDarBackground)
        )
        customSnackbar?.show()
    }
}