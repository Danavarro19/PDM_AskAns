package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentResponderBinding
import com.dnavarro.askanswerviews.databinding.FragmentSearchEncuestaBinding
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel

class fragmentSearchEncuestas: Fragment() {
    private val userModel: Userviewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentSearchEncuestaBinding>(inflater,
            R.layout.fragment_search_encuesta,container,false)
        binding.lifecycleOwner = this
        userModel.resetListoParaEnviar()
        userModel.resetLanzamientos()
        binding.bnvMenuS.selectedItemId = R.id.navEncuestaFragm

        userModel.lanzamientosObtenidos.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(R.id.action_fragmentSearchEncuestas_to_fragment_lanzamientos)
            }else{

            }
        })
        binding.bnvMenuS.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navEnvioFragm -> {
                    userModel.getLanzamientos()
                    true
                }
                R.id.navHomeFragment ->{
                    this.findNavController().navigate(R.id.action_fragmentSearchEncuestas_to_fragment_home)
                    true
                }
                else -> true
            }
        }

        userModel.listoParaEnviar.observe(viewLifecycleOwner, Observer {
            if(it){
                println("listo para enviar: ")
                this.findNavController().navigate(R.id.action_fragmentSearchEncuestas_to_fragmentResponder)
            }else{

            }
        })

        binding.button.setOnClickListener {
            userModel.getEncuestaToResolve(binding.editText.text.toString())
        }




        return binding.root
    }
}