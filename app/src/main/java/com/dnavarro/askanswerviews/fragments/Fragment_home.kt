package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentHomeBinding
import com.dnavarro.askanswerviews.entity.encuesta
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_home : Fragment() {

    private val userModel: Userviewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        userModel.resetUpdateOrCreate()
        userModel.updateEncuestas()
        userModel.pass.observe(viewLifecycleOwner, Observer {
            if(it){
                println("es verdadero")

            }else{
                println("no es verdadero" + it)
                this.findNavController().navigate(R.id.frontPage)
            }
        })

        userModel.ToClearState()
        userModel.Clear()

        userModel.listaEncuesta.observe(viewLifecycleOwner, Observer {  it: MutableCollection<encuesta> ->
            println("encuestas Observer:"+userModel.listaEncuesta.value)
            if(!userModel.listaEncuesta.value!!.isEmpty()){
                println("entra a armas listas")
                binding.listaEncuestas.removeAllViews()
                userModel.listaEncuesta.value!!.forEach {encu ->
                    var newCarEncuesta = CardView(this.context!!)
                    var container = LinearLayout(this.context)
                    container.orientation = LinearLayout.VERTICAL
                    newCarEncuesta.isClickable = true
                    newCarEncuesta.setOnClickListener {
                        //agregar codigo para ir a ver encuesta
                        // ver en tanto Hacer las respuestas en tiempo real
                    }
                    var titulo = TextView(this.context)
                    titulo.setText(encu.nombre_encuesta)
                    var descripcion = TextView(this.context)
                    descripcion.setText(encu.descrip_encuesta)

                    container.addView(titulo)
                    container.addView(descripcion)

                    var containerActions = LinearLayout(this.context)
                    containerActions.orientation = LinearLayout.HORIZONTAL

                    var btn_eliminar = Button(this.context)
                    btn_eliminar.setText("delete")
                    var btn_editar = Button(this.context)
                    btn_editar.setText("edit")

                    btn_eliminar.setOnClickListener {
                        userModel.deleteEncuesta(encu)
                    }

                    btn_editar.setOnClickListener {
                        userModel.ToUpdateState()
                        userModel.Edit(encu)
                        this.findNavController().navigate(R.id.action_fragment_home_to_crearEncuesta)
                    }

                    containerActions.addView(btn_editar)
                    containerActions.addView(btn_eliminar)

                    container.addView(containerActions)
                    newCarEncuesta.addView(container)
                    binding.listaEncuestas.addView(newCarEncuesta)


                }
            }else{
                binding.listaEncuestas.removeAllViews()
                println(it)
                println("la lista no tiene nada" + it)
            }
        })
        binding.crearEncuestaButton.setOnClickListener {
            userModel.Create()
            userModel.ToCreate()
            this.findNavController().navigate(R.id.action_fragment_home_to_crearEncuesta)
        }

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        return binding.root
    }



}
