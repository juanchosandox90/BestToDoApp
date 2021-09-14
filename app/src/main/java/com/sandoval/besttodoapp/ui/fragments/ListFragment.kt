package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*
import com.sandoval.besttodoapp.utils.actionListToAdd
import com.sandoval.besttodoapp.ui.fragments.adapters.ListAdapter
import com.sandoval.besttodoapp.ui.viewmodel.SharedViewModel


class ListFragment : Fragment() {

    private lateinit var navController: NavController
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val listAdapter: ListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        intRecyclerView(view)
        setHasOptionsMenu(true)
        return view
    }

    private fun intRecyclerView(view: View) {
        navController = findNavController()
        val recyclerView = view.recyclerViewList
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        view.addToDoButton.setOnClickListener {
            navController.navigate(actionListToAdd)
        }
        //ViewModel will be used to observe and paint the data that is hosted in the BD. With
        //method getAllData that uses a LiveData
        //TODO: check if the DB is empty and paint the NO DATA UI in the list fragment.
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            listAdapter.setData(data)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.list_fragment_dialog_delete_all_title))
            builder.setMessage(getString(R.string.list_fragment_dialog_delete_all_message))
            builder.setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                mToDoViewModel.deleteAllItems()
                mSharedViewModel.successDialog(
                    requireActivity(),
                    getString(R.string.success_dialog_delete_title),
                    getString(R.string.success_dialog_delete_all_message)
                )
            }
            builder.setNegativeButton(getString(R.string.dialog_no)) { _, _ -> }
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }
}