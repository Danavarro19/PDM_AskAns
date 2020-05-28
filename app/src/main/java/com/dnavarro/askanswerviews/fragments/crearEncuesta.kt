package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentCrearEncuestaBinding
import com.dnavarro.askanswerviews.entity.opcion
import com.dnavarro.askanswerviews.entity.pregunta
import com.dnavarro.askanswerviews.viewmodels.CrearEncuestaViewModel
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.google.android.material.chip.ChipGroup
import org.w3c.dom.Text


/**
 * A simple [Fragment] subclass.
 */
class crearEncuesta : Fragment() {
    private val userModel: Userviewmodel by activityViewModels()
    private val crearEncuesta: CrearEncuestaViewModel by activityViewModels()
//    private val LiveData<Boolean> UpdateOrCreate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCrearEncuestaBinding>(inflater,
            R.layout.fragment_crear_encuesta, container, false)
        binding.lifecycleOwner = this

        //spinner
        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.TipoPreguntas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.tipoPreguntas.adapter = adapter
        }



        //estados
        if(userModel.estado.value == "Update"){

            crearEncuesta.init(userModel.encuestaToUpate.value!!)
        }else{
            if(userModel.estado.value == "Create"){

                crearEncuesta.init(userModel.encuestaToUpate.value!!)
            }else{
                userModel.ToClearState()
                this.findNavController().navigate(R.id.action_crearEncuesta_to_fragment_home)
            }
        }

        userModel.resetUpdateOrCreate()
        userModel.UpdateOrCreated.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(R.id.action_crearEncuesta_to_fragment_home)
            }else{
                println("Ocurrio un Error")
            }
        })

        //updates
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

        //creacion de preguntas
        crearEncuesta.encuesta.value!!.preguntas.forEach {
            var pregunt = CardView(this.context!!)
            //encabezado
            var encabezado = EditText(this.context)
            encabezado.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(p0: Editable) {
                    it.encabezado = p0.toString()
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            pregunt.addView(encabezado)
            var deletePregunta = Button(this.context)
            deletePregunta.setText("-")
            pregunt.addView(deletePregunta)


            var pregunta_abierta = RadioButton(this.context)
            pregunta_abierta.setText("Pregunta Abierta")
            pregunta_abierta.isChecked = it.pregunta_abierta
            pregunta_abierta.setOnClickListener { view ->
                it.pregunta_abierta = pregunta_abierta.isChecked

            }
            pregunt.addView(pregunta_abierta)

            var multi_respuesta = RadioButton(this.context)
            multi_respuesta.setText("Multi Respuestas")
            multi_respuesta.isChecked = it.multi_respuesta
            multi_respuesta.setOnClickListener { view ->
                it.multi_respuesta = multi_respuesta.isChecked
            }
            pregunt.addView(multi_respuesta)

            var requiere = RadioButton(this.context)
            requiere.setText("Obligatoria")
            requiere.isChecked = it.requiere
            requiere.setOnClickListener { view ->
                it.requiere = requiere.isChecked
            }
            pregunt.addView(requiere)

            var contenedorCreateOpciones = LinearLayout(this.context)
            contenedorCreateOpciones.orientation = LinearLayout.HORIZONTAL
            var listaOpciones = EditText(this.context)
            listaOpciones.hint = "Ingresa Texto Opcion"
            var btn_addOption = Button(this.context)
            btn_addOption.setText("+")

            contenedorCreateOpciones.addView(listaOpciones)
            contenedorCreateOpciones.addView(btn_addOption)
            pregunt.addView(contenedorCreateOpciones)

            var contenedorOpciones = LinearLayout(this.context)
            contenedorOpciones.orientation = LinearLayout.VERTICAL
            it.opciones.forEach {op ->
                var contenedorOpcion = LinearLayout(this.context)
                contenedorOpcion.orientation = LinearLayout.HORIZONTAL
                var opcion = EditText(this.context)
                opcion.setText(op.titulo_opcion)
                opcion.addTextChangedListener(object: TextWatcher{
                    override fun afterTextChanged(p0: Editable) {
                        op.titulo_opcion = p0.toString()
                    }
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                })


                var btnDeleteOpcion = Button(this.context)
                btnDeleteOpcion.setText("-")

                btnDeleteOpcion.setOnClickListener { view ->
                    it.opciones.remove(op)
                    contenedorOpciones.removeView(contenedorOpcion)
                }
                contenedorOpcion.addView(opcion)
                contenedorOpcion.addView(btnDeleteOpcion)

                contenedorOpciones.addView(contenedorOpcion)

            }

            btn_addOption.setOnClickListener {  op ->
                var addingOpcion = opcion(listaOpciones.text.toString())
                it.opciones.add(addingOpcion)
                var contenedorOpcion = LinearLayout(this.context)
                contenedorOpcion.orientation = LinearLayout.HORIZONTAL
                var opcion = EditText(this.context)
                opcion.setText(listaOpciones.text)

                opcion.addTextChangedListener(object: TextWatcher{
                    override fun afterTextChanged(p0: Editable) {
                        it.opciones.elementAt(it.opciones.indexOf(addingOpcion)).titulo_opcion = p0.toString()
                        addingOpcion.titulo_opcion = p0.toString()

                    }
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                })

                listaOpciones.setText("")
                var btnDeleteOpcion = Button(this.context)
                btnDeleteOpcion.setText("-")
                btnDeleteOpcion.setOnClickListener { view ->
                    it.opciones.remove(addingOpcion)
                    contenedorOpciones.removeView(contenedorOpcion)
                }
                contenedorOpcion.addView(opcion)
                contenedorOpcion.addView(btnDeleteOpcion)

                contenedorOpciones.addView(contenedorOpcion)
            }

            pregunt.addView(contenedorOpciones)
            deletePregunta.setOnClickListener{
                binding.listaPreguntas.removeView(pregunt)
                crearEncuesta.encuesta.value!!.preguntas.remove(it)
            }

        }
        binding.addPregunta.setText("+")
        binding.addPregunta.setOnClickListener{btn ->
            var newPregunta = pregunta("", binding.tipoPreguntas.selectedItem.toString(),false,false,false,
                mutableListOf())
            var pregunt = CardView(this.context!!)
            //encabezado
            var encabezado = EditText(this.context)
            encabezado.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(p0: Editable) {
                    crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).encabezado = p0.toString()
                    newPregunta.encabezado = p0.toString()
                 }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            pregunt.addView(encabezado)
            var deletePregunta = Button(this.context)
            deletePregunta.setText("-")
            pregunt.addView(deletePregunta)


            var pregunta_abierta = RadioButton(this.context)
            pregunta_abierta.setText("Pregunta Abierta")
            pregunta_abierta.isChecked = newPregunta.pregunta_abierta
            pregunta_abierta.setOnClickListener { view ->
                crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).pregunta_abierta = pregunta_abierta.isChecked

                newPregunta.pregunta_abierta = pregunta_abierta.isChecked

            }
            pregunt.addView(pregunta_abierta)

            var multi_respuesta = RadioButton(this.context)
            multi_respuesta.setText("Multi Respuestas")
            multi_respuesta.isChecked = newPregunta.multi_respuesta
            multi_respuesta.setOnClickListener { view ->
                crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).multi_respuesta = multi_respuesta.isChecked

                newPregunta.multi_respuesta = multi_respuesta.isChecked
            }
            pregunt.addView(multi_respuesta)

            var requiere = RadioButton(this.context)
            requiere.setText("Obligatoria")
            requiere.isChecked = newPregunta.requiere
            requiere.setOnClickListener { view ->
                crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).requiere = requiere.isChecked

                newPregunta.requiere = requiere.isChecked
            }
            pregunt.addView(requiere)

            var contenedorCreateOpciones = LinearLayout(this.context)
            contenedorCreateOpciones.orientation = LinearLayout.HORIZONTAL
            var listaOpciones = EditText(this.context)
            listaOpciones.hint = "Ingresa Texto Opcion"
            var btn_addOption = Button(this.context)
            btn_addOption.setText("+")

            contenedorCreateOpciones.addView(listaOpciones)
            contenedorCreateOpciones.addView(btn_addOption)
            pregunt.addView(contenedorCreateOpciones)

            var contenedorOpciones = LinearLayout(this.context)
            contenedorOpciones.orientation = LinearLayout.VERTICAL

            btn_addOption.setOnClickListener {  op ->
                var addingOpcion = opcion(listaOpciones.text.toString())
                crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).opciones.add(addingOpcion)

                newPregunta.opciones.add(addingOpcion)

                var contenedorOpcion = LinearLayout(this.context)
                contenedorOpcion.orientation = LinearLayout.HORIZONTAL
                var opcion = EditText(this.context)
                opcion.setText(listaOpciones.text)

                opcion.addTextChangedListener(object: TextWatcher{
                    override fun afterTextChanged(p0: Editable) {
                        crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta))
                            .opciones.elementAt(crearEncuesta.encuesta.value!!.preguntas
                                .elementAt(crearEncuesta.encuesta.value!!.preguntas
                                    .indexOf(newPregunta)).opciones.indexOf(addingOpcion)).titulo_opcion = p0.toString()

                        newPregunta.opciones.elementAt(newPregunta.opciones.indexOf(addingOpcion)).titulo_opcion = p0.toString()
                        addingOpcion.titulo_opcion = p0.toString()

                    }
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                })

                listaOpciones.setText("")
                var btnDeleteOpcion = Button(this.context)
                btnDeleteOpcion.setText("-")

                btnDeleteOpcion.setOnClickListener { view ->
                    crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta))
                        .opciones.remove(addingOpcion)
                    newPregunta.opciones.remove(addingOpcion)
                    contenedorOpciones.removeView(contenedorOpcion)
                }
                contenedorOpcion.addView(opcion)
                contenedorOpcion.addView(btnDeleteOpcion)

                contenedorOpciones.addView(contenedorOpcion)
            }

            pregunt.addView(contenedorOpciones)
            deletePregunta.setOnClickListener{
                binding.listaPreguntas.removeView(pregunt)
                crearEncuesta.encuesta.value!!.preguntas.remove(newPregunta)
            }

        }

        binding.guardar.setOnClickListener{
            userModel.createOrUpdate(crearEncuesta.getEncuesta())
        }



        return binding.root;
    }

}
