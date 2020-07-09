package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentCrearLanzamientosBinding
import com.dnavarro.askanswerviews.databinding.FragmentHomeBinding
import com.dnavarro.askanswerviews.entity.lanzamientos
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.google.android.material.chip.Chip

class fragment_crear_lanzamiento: Fragment() {

    private val userModel: Userviewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCrearLanzamientosBinding>(inflater,
            R.layout.fragment_crear_lanzamientos, container, false)
        binding.lifecycleOwner = this
        userModel.resetLnazamientoCreado()
        userModel.lanzamientocreado.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(R.id.action_fragment_crear_lanzamiento_to_fragment_lanzamientos)
            }else{

            }
        })

        var newLa = lanzamientos("","","",0, mutableListOf(),false,0,false,0)
        var listaId: MutableList<String> = mutableListOf()
        var listaNombres: MutableList<String> = mutableListOf()
        userModel.listaEncuesta.value!!.forEach {
            listaId.add(it._id)
            listaNombres.add(it.nombre_encuesta)

        }
        binding.listaEncuestaSpiner.adapter = ArrayAdapter(this.context!!,R.layout.support_simple_spinner_dropdown_item,listaNombres)


        binding.imageButton.setOnClickListener{
            var chip = Chip(this.context!!)
            chip.text = binding.editTextTextPersonName.text
            binding.editTextTextPersonName.setText("")
            chip.isCloseIconVisible = true
            chip.isClickable = false
            chip.isCheckable = false
            binding.grupoChips.addView(chip)
            newLa.tags_publico.add(chip.text.toString())

            chip.setOnCloseIconClickListener {
                newLa.tags_publico.remove(chip.text.toString())
                binding.grupoChips.removeView(chip)
            }
        }

        binding.button4.setOnClickListener {

           try {
               newLa.encuesta = listaId.get(binding.listaEncuestaSpiner.selectedItemPosition ?: 0)
               newLa.cantidad_usuario =  binding.editTextNumber.text.toString().toInt()
               userModel.createLanzamiento(newLa)
           }  catch (e: Exception){
                println(e.message)
           }


        }

        return binding.root
    }
}