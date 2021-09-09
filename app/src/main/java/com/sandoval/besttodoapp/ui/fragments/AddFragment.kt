package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.models.Priority
import com.sandoval.besttodoapp.data.models.ToDoData
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private lateinit var navController: NavController
    private var actionAddToList: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        actionAddToList = R.id.action_addFragment_to_listFragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {
        val mTitle = addToDoTitle.text.toString()
        //val mPriority = addToDoPriority.selectedItem.toString()
        val mDescription = addToDoDescription.text.toString()
        //Here we need to validate if this inputs are not null or empty.
        val validation = verifyDataFromUserInput(mTitle, mDescription)
        if (validation) {
            //TODO: A converter function to transform the ENUM of Priority to String. in that way wont be hardcoded HIGH
            val newData = ToDoData(
                0,
                mTitle,
                Priority.HIGH,
                mDescription
            )
            // Function insertData to DB is needed to complete the process. newData obj we will be passed in this fun.
            // As the DAO is using a suspend function, this process has to be done in a couroutine.
            // the best practice to do this is using a ViewModel to access a dispatcher and in that
            // way the UI wont be blocked for the user while the insertion process is done.
            //TODO: Add function InsertData been called by the ViewModel Class
            Toast.makeText(requireContext(), "Data has been added succesfully!", Toast.LENGTH_LONG)
                .show().apply {

                }
            navController.navigate(actionAddToList!!)


        } else {
            Toast.makeText(
                requireContext(),
                "Please make sure you fill all fields, they are mandatory!",
                Toast.LENGTH_LONG
            )
                .show()
        }

    }

    private fun verifyDataFromUserInput(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

}