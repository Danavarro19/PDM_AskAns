package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentCrearEncuestaBinding
import com.dnavarro.askanswerviews.viewmodels.CrearEncuestaViewModel
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import org.w3c.dom.Text


/**
 * A simple [Fragment] subclass.
 */
class crearEncuesta : Fragment() {
    private val userModel: Userviewmodel by activityViewModels()
    private val crearEncuesta: CrearEncuestaViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCrearEncuestaBinding>(inflater,
            R.layout.fragment_crear_encuesta, container, false)
        binding.lifecycleOwner = this

        if(userModel.estado.value == "Update"){
            userModel.ToClearState()
            userModel.ToCreate()
            crearEncuesta.init(userModel.encuestaToUpate.value!!)
        }else{
            if(userModel.estado.value == "Update"){
                userModel.ToClearState()
                userModel.ToUpdateState()
                crearEncuesta.init(userModel.encuestaToUpate.value!!)
            }else{
                userModel.ToClearState()
                this.findNavController().navigate(R.id.action_crearEncuesta_to_fragment_home)
            }
        }
        binding.nombre.setText(crearEncuesta.encuesta.value!!.nombre_encuesta)
        binding.descripcion.setText(crearEncuesta.encuesta.value!!.descrip_encuesta)

        binding.nombre.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable) {
                crearEncuesta.updateNombre(p0.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        binding.descripcion.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable) {
                crearEncuesta.update_descripcion(p0.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        return binding.root;
    }

}
