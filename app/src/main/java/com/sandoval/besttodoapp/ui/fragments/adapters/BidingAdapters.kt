package com.sandoval.besttodoapp.ui.fragments.adapters

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.models.Priority
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.ui.fragments.ListFragmentDirections
import com.sandoval.besttodoapp.utils.actionListToAdd

class BidingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(actionListToAdd)
                }
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(spinner: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> spinner.setSelection(0)
                Priority.MEDIUM -> spinner.setSelection(1)
                Priority.LOW -> spinner.setSelection(2)
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardview: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> cardview.setBackgroundResource(R.drawable.circie_indicator_shape_priority_high)
                Priority.MEDIUM -> cardview.setBackgroundResource(R.drawable.circie_indicator_shape_priority_medium)
                Priority.LOW -> cardview.setBackgroundResource(R.drawable.circie_indicator_shape_priority_low)
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

    }
}