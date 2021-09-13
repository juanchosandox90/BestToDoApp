package com.sandoval.besttodoapp.ui.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.data.viewmodel.ToDoViewModel
import com.sandoval.besttodoapp.ui.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import com.sandoval.besttodoapp.utils.actionAddToList

class AddFragment : Fragment() {

    private lateinit var navController: NavController
    private val mTodoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        view.addToDoPriority.onItemSelectedListener = mSharedViewModel.listener
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
        val mPriority = addToDoPriority.selectedItem.toString()
        val mDescription = addToDoDescription.text.toString()
        //Here we need to validate if this inputs are not null or empty.
        val validation = mSharedViewModel.verifyDataFromUserInput(mTitle, mDescription)
        if (validation) {
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            // Function insertData to DB is needed to complete the process. newData obj we will be passed in this fun.
            // As the DAO is using a suspend function, this process has to be done in a coroutine.
            // the best practice to do this is using a ViewModel to access a dispatcher and in that
            // way the UI wont be blocked for the user while the insertion process is done.
            mTodoViewModel.insertData(newData)
            mSharedViewModel.hideSoftKeyboard(requireActivity())
            navController.navigate(actionAddToList)


        } else {
            mSharedViewModel.hideSoftKeyboard(requireActivity())
            mSharedViewModel.errorDialog(requireActivity())
        }

    }
}