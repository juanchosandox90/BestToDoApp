package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.data.viewmodel.ToDoViewModel
import com.sandoval.besttodoapp.ui.viewmodel.SharedViewModel
import com.sandoval.besttodoapp.utils.actionUpdateToList
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    //Safe args are create to be able to get the data that the List Fragment is sending through the
    //adapter
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: ToDoViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        initViews(view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
            updateToDoItem()
        } else if (item.itemId == R.id.menu_delete) {
            Toast.makeText(requireContext(), "Delete Option under construction", Toast.LENGTH_LONG)
                .show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateToDoItem() {
        val mTitle = updateToDoTitle.text.toString()
        val mPriority = updateToDoPriority.selectedItem.toString()
        val mDescription = updateToDoDescription.text.toString()
        //Here we need to validate if this inputs are not null or empty.
        val validation = mSharedViewModel.verifyDataFromUserInput(mTitle, mDescription)
        if (validation) {
            val updateData = ToDoData(
                args.currentItem.id,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            // Function insertData to DB is needed to complete the process. newData obj we will be passed in this fun.
            // As the DAO is using a suspend function, this process has to be done in a coroutine.
            // the best practice to do this is using a ViewModel to access a dispatcher and in that
            // way the UI wont be blocked for the user while the insertion process is done.
            //getString(R.string.update_fragment_dialog_title_success)
            //getString(R.string.update_fragment_dialog_message_success)
            mTodoViewModel.updateData(updateData)
            mSharedViewModel.hideSoftKeyboard(requireActivity())
            mSharedViewModel.successDialog(
                requireActivity(),
                getString(R.string.update_fragment_dialog_title_success),
                getString(R.string.update_fragment_dialog_message_success)
            )
            navController.navigate(actionUpdateToList)


        } else {
            mSharedViewModel.hideSoftKeyboard(requireActivity())
            mSharedViewModel.errorDialog(requireActivity())
        }
    }

    private fun initViews(view: View) {
        view.updateToDoTitle.setText(args.currentItem.title)
        view.updateToDoDescription.setText(args.currentItem.description)
        view.updateToDoPriority.setSelection(mSharedViewModel.parsePriorityInt(args.currentItem.priority))
        view.updateToDoPriority.onItemSelectedListener = mSharedViewModel.listener
    }
}