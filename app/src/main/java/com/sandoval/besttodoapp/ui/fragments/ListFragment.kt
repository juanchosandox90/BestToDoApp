package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.viewmodel.ToDoViewModel
import com.sandoval.besttodoapp.databinding.FragmentListBinding
import com.sandoval.besttodoapp.ui.fragments.adapters.ListAdapter
import com.sandoval.besttodoapp.ui.viewmodel.SharedViewModel
import com.sandoval.besttodoapp.utils.SwipeToDelete
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

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
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = OvershootInRightAnimator().apply {
            addDuration = 400
        }
        recyclerView.adapter = ScaleInAnimationAdapter(listAdapter).apply {
            setFirstOnly(false)
            setDuration(400)
            setInterpolator(OvershootInterpolator(0.5f))
        }

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mSharedViewModel.checkIfDatabaseIsEmpty(data)
            listAdapter.setData(data)
        })
        swipeToDeleteItem(recyclerView)
    }

    //Function to delete an item using the swipe gesture
    private fun swipeToDeleteItem(recyclerView: RecyclerView) {
        //Overriding the onSwiped makes that the method onSwiped from the class SwipeToDelete
        //can be erased.
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = listAdapter.dataList[viewHolder.adapterPosition]
                mToDoViewModel.deleteSingleItem(deletedItem)
                mToDoViewModel.restoreDeleteItem(
                    viewHolder.itemView,
                    deletedItem,
                    viewHolder.adapterPosition,
                    listAdapter
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        initSearchViewOption(menu)
    }

    private fun initSearchViewOption(menu: Menu) {
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle(getString(R.string.list_fragment_dialog_delete_all_title))
                builder.setMessage(getString(R.string.list_fragment_dialog_delete_all_message))
                builder.setPositiveButton(R.string.dialog_yes) { _, _ ->
                    mToDoViewModel.deleteAllItems()
                    mSharedViewModel.successDialog(
                        requireActivity(),
                        (R.string.success_dialog_delete_title).toString(),
                        (R.string.success_dialog_delete_all_message).toString()
                    )
                }
                builder.setNegativeButton(R.string.dialog_no) { _, _ -> }
                builder.create().show()
            }
            R.id.menu_priority_high -> sortByHighPriority()
            R.id.menu_priority_medium -> sortByMediumPriority()
            R.id.menu_priority_low -> sortByLowPriority()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortByLowPriority() {
        mToDoViewModel.sortByLowPriority.observe(this, {
            listAdapter.setData(it)
        })
    }

    private fun sortByMediumPriority() {
        mToDoViewModel.sortByMediumPriority.observe(this, {
            listAdapter.setData(it)
        })
    }

    private fun sortByHighPriority() {
        mToDoViewModel.sortByHighPriority.observe(this, {
            listAdapter.setData(it)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
            mSharedViewModel.hideSoftKeyboard(requireActivity())
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"
        mToDoViewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                listAdapter.setData(it)
            }
        })
    }
}