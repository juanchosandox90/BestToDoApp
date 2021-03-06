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
import com.sandoval.besttodoapp.databinding.FragmentAddBinding
import com.sandoval.besttodoapp.ui.viewmodel.SharedViewModel
import com.sandoval.besttodoapp.utils.actionAddToList

class AddFragment : Fragment() {

    private lateinit var navController: NavController
    private val mTodoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        initViews()
        mSharedViewModel.hideSoftKeyboard(requireActivity())
        setHasOptionsMenu(true)
        return binding.root
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
        val mTitle = binding.addToDoTitle.text.toString()
        val mPriority = binding.addToDoPriority.selectedItem.toString()
        val mDescription = binding.addToDoDescription.text.toString()
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
            mSharedViewModel.successDialog(
                requireActivity(),
                getString(R.string.add_fragment_dialog_title_success),
                getString(R.string.add_fragment_dialog_message_success)
            )
            navController.navigate(actionAddToList)


        } else {
            mSharedViewModel.hideSoftKeyboard(requireActivity())
            mSharedViewModel.errorDialog(requireActivity())
        }

    }

    private fun initViews() {
        navController = findNavController()
        binding.addToDoPriority.onItemSelectedListener = mSharedViewModel.listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}