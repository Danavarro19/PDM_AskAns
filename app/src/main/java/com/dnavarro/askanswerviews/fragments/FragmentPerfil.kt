package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentHomeBinding
import com.dnavarro.askanswerviews.databinding.FragmentPerfilBinding
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel

class FragmentPerfil : Fragment() {

    private val userModel: Userviewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentPerfilBinding>(inflater,
            R.layout.fragment_perfil, container, false)
        binding.lifecycleOwner = this

        binding.userModel = userModel




        return binding.root
    }
}