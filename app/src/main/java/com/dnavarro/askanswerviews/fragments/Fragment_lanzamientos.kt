package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentHomeBinding
import com.dnavarro.askanswerviews.databinding.FragmentLanzamientosBinding
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_search_encuesta.*
import org.w3c.dom.Text
import kotlin.math.cos

class Fragment_lanzamientos: Fragment() {

    private val userModel: Userviewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLanzamientosBinding>(inflater,
            R.layout.fragment_lanzamientos, container, false)
        binding.lifecycleOwner = this
        binding.bnvMenuS.selectedItemId = R.id.navEnvioFragm
        binding.bnvMenuS.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navEncuestaFragm -> {
                    this.findNavController().navigate(R.id.action_fragment_lanzamientos_to_fragmentSearchEncuestas)
                    true
                }
                R.id.navHomeFragment ->{
                    this.findNavController().navigate(R.id.action_fragment_lanzamientos_to_fragment_home)
                    true
                }
                else -> true

            }
        }

        userModel.ListaLanzamientos.observe(viewLifecycleOwner, Observer {
            binding.listaLanzamientos.removeAllViews()
            it.forEach {lan ->
                var cardContainer = CardView(this.context!!)

                var VerticalContainer = LinearLayout(this.context)
                VerticalContainer.orientation = LinearLayout.VERTICAL

                var actionIconsContainer = LinearLayout(this.context)
                actionIconsContainer.orientation = LinearLayout.HORIZONTAL

                var titulo = TextView(this.context!!)
                VerticalContainer.addView(titulo)

                var nombreEncuesta: String = ""
                userModel.listaEncuesta.value!!.forEach {
                    if(it._id.equals(lan.encuesta)){
                        nombreEncuesta = it.nombre_encuesta
                    }
                }
                if(nombreEncuesta.isNotEmpty()){
                    titulo.text = "Encuesta:" +nombreEncuesta
                }

                var containerChips = ChipGroup(this.context)
                lan.tags_publico.forEach {
                    var chip = Chip(this.context)
                    chip.text = it
                    containerChips.addView(chip)
                }
                VerticalContainer.addView(containerChips)

                var cantidad_users = TextView(this.context)
                cantidad_users.text = "Alcance: " + lan.cantidad_usuario + " usuarios"
                VerticalContainer.addView(cantidad_users)

                var statusPago = TextView(this.context)
                VerticalContainer.addView(statusPago)

                if(lan.pagada){
                    statusPago.text = "Status: Pagada"
                    var cantidad_respuesta = TextView(this.context)
                    cantidad_respuesta.text = "" + lan.cantidad_respuesta + "/" + lan.cantidad_usuario + " respuestas"
                    VerticalContainer.addView(cantidad_respuesta)
                    var btndescargar = Button(this.context)
                    btndescargar.text = "Descargar Factura"
                    btndescargar.setOnClickListener {

                    }
                }else{
                    statusPago.text = "Status: No Pagada"
                    var costo = TextView(this.context)
                    costo.text = "Costo: $" + lan.costo.toString()
                    VerticalContainer.addView(costo)

                    var btnborrar = Button(this.context)
                    btnborrar.text = "Eliminar"
                    btnborrar.setOnClickListener {
                        userModel.deleteLanzamiento(lan._id)
                    }
                    var btnPagar = Button(this.context)
                    btnPagar.text = "Pagar"
                    btnPagar.setOnClickListener {
                        userModel.UpdateLanzamientoPagar(lan._id)
                        this.findNavController().navigate(R.id.fragment_pagar)
                    }
                    actionIconsContainer.addView(btnborrar)
                    actionIconsContainer.addView(btnPagar)
                    VerticalContainer.addView(actionIconsContainer)

                }

                cardContainer.addView(VerticalContainer)
                binding.listaLanzamientos.addView(cardContainer)


            }
        })

        binding.button2.setOnClickListener {
            this.findNavController().navigate(R.id.action_fragment_lanzamientos_to_fragment_crear_lanzamiento)
        }

        return binding.root
    }
}