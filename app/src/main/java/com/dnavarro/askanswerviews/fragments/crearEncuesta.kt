package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentCrearEncuestaBinding


/**
 * A simple [Fragment] subclass.
 */
class crearEncuesta : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCrearEncuestaBinding>(inflater,
            R.layout.fragment_crear_encuesta, container, false)
        binding.lifecycleOwner = this




        return binding.root;
    }

}
