package com.sandoval.besttodoapp.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.findNavController
import com.sandoval.besttodoapp.data.models.Priority
import com.thecode.aestheticdialogs.*
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.utils.highPriorityString
import com.sandoval.besttodoapp.utils.mediumPriorityString
import com.sandoval.besttodoapp.utils.lowPriorityString

//This viewModelclass will be shared with the whole project so thats why is been isolated
class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.redPriority
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellowPriority
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.greenPriority
                        )
                    )
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

    }

    fun verifyDataFromUserInput(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            highPriorityString -> Priority.HIGH
            mediumPriorityString -> Priority.MEDIUM
            lowPriorityString -> Priority.LOW
            else -> Priority.LOW
        }
    }

    fun parsePriorityInt(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

    fun errorDialog(activity: Activity) {
        AestheticDialog.Builder(activity, DialogStyle.RAINBOW, DialogType.ERROR)
            .setTitle(activity.getString(R.string.add_fragment_missing_fields_error))
            .setMessage(activity.getString(R.string.add_fragment_missing_fields_message_error))
            .setCancelable(true)
            .setDarkMode(false)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.FADE)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    dialog.dismiss()
                }
            })
            .show()
    }

    fun successDialog(activity: Activity, title: String, message: String) {
        AestheticDialog.Builder(activity, DialogStyle.RAINBOW, DialogType.SUCCESS)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .setDarkMode(false)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.FADE)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    dialog.dismiss()
                }
            })
            .show()
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive) {
            if (activity.currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            }
        }
    }

}