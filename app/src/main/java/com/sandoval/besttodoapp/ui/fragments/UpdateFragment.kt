package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.data.viewmodel.ToDoViewModel
import com.sandoval.besttodoapp.databinding.FragmentUpdateBinding
import com.sandoval.besttodoapp.ui.viewmodel.SharedViewModel
import com.sandoval.besttodoapp.utils.actionUpdateToList
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateFragment : Fragment() {

    //Safe args are create to be able to get the data that the List Fragment is sending through the
    //adapter
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: ToDoViewModel by viewModels()
    private lateinit var navController: NavController

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        binding.updateToDoPriority.onItemSelectedListener = mSharedViewModel.listener
        navController = findNavController()
        //initViews(view)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateToDoItem()
            R.id.menu_delete -> deleteSingleItem()
        }
        return super.onOptionsItemSelected(item)
    }

    // This function deletes a single item using the safe args currentItem selected.
    // as the Safe Arg we are passing through the action is an obj of type ToDoData and is parsed
    // using the parcelable, we are able to access the whole object or his properties. This is how
    // makes more easy the deletion process of the DB.

    private fun deleteSingleItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete '${args.currentItem.title}'")
        builder.setMessage("Are you sure you want to delete '${args.currentItem.title}'?")
        builder.setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
            mTodoViewModel.deleteSingleItem(args.currentItem)
            mSharedViewModel.successDialog(
                requireActivity(),
                getString(R.string.success_dialog_delete_title),
                getString(R.string.success_dialog_delete_single_message)
            )
            findNavController().navigate(actionUpdateToList)
        }
        builder.setNegativeButton(getString(R.string.dialog_no)) { _, _ -> }
        builder.create().show()
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

            // Function updateData to DB is needed to update the information. updateData obj we will
            // be passed in this fun. As the DAO is using a suspend function, this process has to be
            // done in a coroutine.

            // the best practice to do this is using a ViewModel to access a dispatcher and in that
            // way the UI wont be blocked for the user while the insertion process is done.

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}