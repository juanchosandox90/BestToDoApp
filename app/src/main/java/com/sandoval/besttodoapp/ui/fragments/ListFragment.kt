package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
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


class ListFragment : Fragment() {

    private lateinit var navController: NavController
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val listAdapter: ListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = findNavController()
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        intRecyclerView(view)

        //ViewModel will be used to observe and paint the data that is hosted in the BD. With
        //method getAllData that uses a LiveData
        //TODO: check if the DB is empty and paint the NO DATA UI in the list fragment.
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            listAdapter.setData(data)
        })

        view.addToDoButton.setOnClickListener {
            navController.navigate(actionListToAdd)

        }

        setHasOptionsMenu(true)

        return view
    }

    private fun intRecyclerView(view: View) {
        val recyclerView = view.recyclerViewList
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}