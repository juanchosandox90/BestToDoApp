package com.sandoval.besttodoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.sandoval.besttodoapp.R

class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

}