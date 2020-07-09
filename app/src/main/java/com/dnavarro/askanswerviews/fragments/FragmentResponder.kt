package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dnavarro.askanswerviews.databinding.FragmentResponderBinding
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.entity.respuestas
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.dnavarro.askanswerviews.entity.respuesta

class FragmentResponder : Fragment() {

    private val userModel: Userviewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentResponderBinding>(inflater,R.layout.fragment_responder,container,false)
        binding.lifecycleOwner = this
        userModel.resetEnviarRespuesta()
        userModel.respuestaEnviada.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(R.id.action_fragmentResponder_to_fragment_home)
            }else{

                Log.i("Observer","es un no se ha hecho")
            }
        })

        var respuesta = respuestas("",userModel.encuestaToResolve.value!!._id,false, mutableListOf(),"","")

        binding.titulo.text = userModel.encuestaToResolve.value!!.nombre_encuesta
        binding.descripcion.text = userModel.encuestaToResolve.value!!.descrip_encuesta

        userModel.encuestaToResolve.value!!.preguntas.forEach {


            var newCardPregunta = CardView(this.context!!)
            newCardPregunta.getBackground().setAlpha(0)
            newCardPregunta.setContentPadding(0,15,0,15)
            newCardPregunta.setCardElevation(0F)
            var container = LinearLayout(this.context)
            container.orientation = LinearLayout.VERTICAL
            newCardPregunta.isClickable = false

            when(it.tipo){
                "Respuesta abierta." -> {
                    var resp = respuesta(it.encabezado,"","")
                    respuesta.respuesta.add(resp)
                    var titulo = TextView(this.context)
                    titulo.setTextSize(3, 10.0F)
                    titulo.text = it.encabezado
                    var respuestae = EditText(this.context)
                    respuestae.hint = "Escribe tu respuesta"
                    respuestae.setTextSize(3, 8.5F)
                    respuestae.addTextChangedListener(object: TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            resp.id_respuesta = "none"
                            resp.respuesta_abierta = s.toString()
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    })
                    container.addView(titulo)
                    container.addView(respuestae)
                }
                "Opcion Multiple." -> {
                    var titulo = TextView(this.context)
                    titulo.text = it.encabezado


                    if(it.pregunta_abierta){
                        var resp = respuesta(it.encabezado,"","")
                        respuesta.respuesta.add(resp)
                        var opcionesRadio = RadioGroup(this.context)




                        var respuestae = EditText(this.context)
                        respuestae.hint = "Escribe tu respuesta"
                        respuestae.visibility = EditText.INVISIBLE
                        respuestae.addTextChangedListener(object: TextWatcher {
                            override fun afterTextChanged(s: Editable?) {
                                resp.id_respuesta = s.toString()
                                resp.respuesta_abierta = s.toString()
                            }

                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                        })

                        var other = RadioButton(this.context)
                        other.text = "otro"
                        other.setOnClickListener { v ->
                            respuestae.visibility = EditText.VISIBLE
                        }

                        it.opciones.forEach {
                            var opcion = RadioButton(this.context)
                            opcion.text = it.titulo_opcion

                            opcion.setOnClickListener {v ->
                                resp.id_respuesta = it.titulo_opcion
                                respuestae.visibility = EditText.INVISIBLE

                            }
                            opcionesRadio.addView(opcion)
                        }
                        opcionesRadio.addView(other)

                        container.addView(titulo)
                        container.addView(opcionesRadio)
                        container.addView(respuestae)


                        //pregunta abierta y opcion multiple
                    }else{
                        if(it.multi_respuesta){

                            container.addView(titulo)
                            it.opciones.forEach {v ->

                                var opcion = CheckBox(this.context)
                                var resp = respuesta(it.encabezado,v.titulo_opcion,"")
                                opcion.text = v.titulo_opcion

                                opcion.setOnClickListener {vc ->
                                    if(respuesta.respuesta.contains(resp)){
                                        respuesta.respuesta.remove(resp)
                                    }else{
                                        respuesta.respuesta.add(resp)
                                    }


                                }
                                container.addView(opcion)

                            }




                            //multirespuesta sin pregunta abierta
                        }else{
                            //opcion multiple sin pregunta abierta
                            var resp = respuesta(it.encabezado,"","")
                            respuesta.respuesta.add(resp)
                            var opcionesRadio = RadioGroup(this.context)

                            it.opciones.forEach {
                                var opcion = RadioButton(this.context)
                                opcion.text = it.titulo_opcion

                                opcion.setOnClickListener {v ->
                                    resp.id_respuesta = it.titulo_opcion


                                }
                                opcionesRadio.addView(opcion)
                            }

                            container.addView(titulo)
                            container.addView(opcionesRadio)
                        }
                    }
                }
            }


            //aqui
            newCardPregunta.addView(container)
            binding.listaPreguntas.addView(newCardPregunta)
        }

        binding.Enviar.setOnClickListener {
            userModel.makeAnswer(respuesta)
        }

        return binding.root
    }


}