package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.viewmodel.ToDoViewModel
import com.sandoval.besttodoapp.databinding.FragmentListBinding
import com.sandoval.besttodoapp.ui.fragments.adapters.ListAdapter
import com.sandoval.besttodoapp.ui.viewmodel.SharedViewModel

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val listAdapter: ListAdapter by lazy {
        ListAdapter()
    }
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel
        intRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun intRecyclerView() {
        val recyclerView = binding.recyclerViewList
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mSharedViewModel.checkIfDatabaseIsEmpty(data)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}