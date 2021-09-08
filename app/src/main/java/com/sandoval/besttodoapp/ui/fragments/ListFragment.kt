package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sandoval.besttodoapp.R
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    lateinit var navController: NavController
    private var actionListToAdd: Int? = null
    private var actionListToUpdate: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        actionListToAdd = R.id.action_listFragment_to_addFragment
        actionListToUpdate = R.id.action_listFragment_to_updateFragment

        view.addToDoButton.setOnClickListener {
            navController.navigate(actionListToAdd!!)

        }

        view.listLayout.setOnClickListener {
            navController.navigate(actionListToUpdate!!)
        }

        setHasOptionsMenu(true)

        return view
    }
}