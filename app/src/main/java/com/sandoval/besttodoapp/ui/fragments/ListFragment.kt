package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sandoval.besttodoapp.R
import kotlinx.android.synthetic.main.fragment_list.view.*
import com.sandoval.besttodoapp.utils.actionListToAdd
import com.sandoval.besttodoapp.utils.actionListToUpdate


class ListFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        val view = inflater.inflate(R.layout.fragment_list, container, false)


        view.addToDoButton.setOnClickListener {
            navController.navigate(actionListToAdd)

        }

        view.listLayout.setOnClickListener {
            navController.navigate(actionListToUpdate)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}