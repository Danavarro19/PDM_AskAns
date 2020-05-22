package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class Fragment_home : Fragment() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        return binding.root
    }

}
