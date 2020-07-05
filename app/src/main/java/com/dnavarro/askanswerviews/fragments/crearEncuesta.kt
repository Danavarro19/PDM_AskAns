package com.dnavarro.askanswerviews.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.R.font
import com.dnavarro.askanswerviews.databinding.FragmentCrearEncuestaBinding
import com.dnavarro.askanswerviews.entity.opcion
import com.dnavarro.askanswerviews.entity.pregunta
import com.dnavarro.askanswerviews.viewmodels.CrearEncuestaViewModel
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel


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
            var containerPrincipal = LinearLayout(this.context)
            //setCardBackgroundColor(Color.rgb(192,224,231))
            //getBackground().setAlpha(60);
            //pregunt.setBackgroundColor(Color.rgb(192,224,231))
            pregunt.setCardBackgroundColor(Color.TRANSPARENT);
            pregunt.setCardElevation(0F);
            containerPrincipal.orientation = LinearLayout.VERTICAL
            //encabezado
            var encabezado = EditText(this.context)
            encabezado.setText(it.encabezado)
            encabezado.addTextChangedListener(object: TextWatcher{

                override fun afterTextChanged(p0: Editable) {
                    it.encabezado = p0.toString()
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            containerPrincipal.addView(encabezado)
            var deletePregunta = Button(this.context)
            deletePregunta.setText("-")
            containerPrincipal.addView(deletePregunta)


            var pregunta_abierta = RadioButton(this.context)
            pregunta_abierta.setText("Pregunta Abierta")
            pregunta_abierta.isChecked = it.pregunta_abierta
            pregunta_abierta.setOnClickListener { view ->
                it.pregunta_abierta = pregunta_abierta.isChecked

            }
            containerPrincipal.addView(pregunta_abierta)

            var multi_respuesta = RadioButton(this.context)
            multi_respuesta.setText("Multi Respuestas")
            multi_respuesta.isChecked = it.multi_respuesta
            multi_respuesta.setOnClickListener { view ->
                it.multi_respuesta = multi_respuesta.isChecked
            }
            containerPrincipal.addView(multi_respuesta)

            var requiere = RadioButton(this.context)
            requiere.setText("Obligatoria")
            requiere.isChecked = it.requiere
            requiere.setOnClickListener { view ->
                it.requiere = requiere.isChecked
            }
            containerPrincipal.addView(requiere)

            var contenedorCreateOpciones = LinearLayout(this.context)
            contenedorCreateOpciones.orientation = LinearLayout.HORIZONTAL
            var listaOpciones = EditText(this.context)
            listaOpciones.hint = "Ingresa Texto Opcion"
            var btn_addOption = Button(this.context)
            btn_addOption.setText("+")

            contenedorCreateOpciones.addView(listaOpciones)
            contenedorCreateOpciones.addView(btn_addOption)
            containerPrincipal.addView(contenedorCreateOpciones)

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

            containerPrincipal.addView(contenedorOpciones)
            pregunt.addView(containerPrincipal)
            binding.listaPreguntas.addView(pregunt)
            deletePregunta.setOnClickListener{del ->
                binding.listaPreguntas.removeView(pregunt)
                crearEncuesta.encuesta.value!!.preguntas.remove(it)
            }

        }


        binding.addPregunta.setText("+")
        binding.addPregunta.setOnClickListener{btn ->
            var newPregunta = pregunta("", binding.tipoPreguntas.selectedItem.toString(),false,false,false,
                mutableListOf())
            crearEncuesta.encuesta.value!!.preguntas.add(newPregunta)
            var pregunt = CardView(this.context!!)
            pregunt.getBackground().setAlpha(51)
            pregunt.setContentPadding(0,15,0,15)

            var containerPrincipal = LinearLayout(this.context)
            containerPrincipal.orientation = LinearLayout.VERTICAL

            //encabezado
            var encabezado = EditText(this.context)
            encabezado.hint = "Título de la pregunta"
            //val font = Typeface.createFromAsset()
            //encabezado.typeface = Typeface.
            //encabezado.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/averia_sans_libre_ligth.xml")
            //val typeface = ResourcesCompat.getFont(this, font.averia_sans_libre_light)
            //encabezado.setTypeface(typeface)
            encabezado.setTextSize(3, 8.5F)
            encabezado.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(p0: Editable) {
                    println("element at:" + crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta))
                    crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).encabezado = p0.toString()
                    newPregunta.encabezado = p0.toString()
                 }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            containerPrincipal.addView(encabezado)
            var deletePregunta = Button(this.context)
            //deletePregunta.setText("-")
            deletePregunta.setTextSize(3, 8.5F)
            deletePregunta.setLayoutParams(LinearLayout.LayoutParams(75, 75))
            deletePregunta.getBackground().setAlpha(0);
            deletePregunta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_remove_circle_outline_24, 0, 0, 0);
            deletePregunta.setTextColor(Color.RED);
            //deletePregunta.setLayoutParams(LinearLayout.LayoutParams(75, 200))
            //deletePregunta.setBackgroundColor(Color.rgb(38,153,251))
            //deletePregunta.setBackgroundResource(Color.rgb(38,153,251))
            //setTextColor(Color.rgb(38,153,251))
            containerPrincipal.addView(deletePregunta)



            var pregunta_abierta = CheckBox(this.context)
            pregunta_abierta.setText("Pregunta Abierta")
            pregunta_abierta.setTextSize(3, 7.5F)
            pregunta_abierta.isChecked = newPregunta.pregunta_abierta
            pregunta_abierta.setOnClickListener { view ->
                crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).pregunta_abierta = pregunta_abierta.isChecked

                newPregunta.pregunta_abierta = pregunta_abierta.isChecked

            }
            containerPrincipal.addView(pregunta_abierta)

            var multi_respuesta = CheckBox(this.context)
            multi_respuesta.setText("Multi Respuestas")
            multi_respuesta.setTextSize(3, 7.5F)
            multi_respuesta.isChecked = newPregunta.multi_respuesta
            multi_respuesta.setOnClickListener { view ->
                crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).multi_respuesta = multi_respuesta.isChecked

                newPregunta.multi_respuesta = multi_respuesta.isChecked
            }
            containerPrincipal.addView(multi_respuesta)

            var requiere = CheckBox(this.context)
            requiere.setText("Respuesta obligatoria")
            requiere.setTextSize(3, 7.5F)
            requiere.isChecked = newPregunta.requiere
            requiere.setOnClickListener { view ->
                crearEncuesta.encuesta.value!!.preguntas.elementAt(crearEncuesta.encuesta.value!!.preguntas.indexOf(newPregunta)).requiere = requiere.isChecked

                newPregunta.requiere = requiere.isChecked
            }
            containerPrincipal.addView(requiere)

            var contenedorCreateOpciones = LinearLayout(this.context)
            contenedorCreateOpciones.orientation = LinearLayout.HORIZONTAL
            var listaOpciones = EditText(this.context)
            listaOpciones.hint = "Ingresar texto opción    "
            listaOpciones.setTextSize(3, 8.5F)

            var btn_addOption = Button(this.context)
            //btn_addOption.setText("+")
            btn_addOption.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_circle_outline_24, 0, 0, 0)
            btn_addOption.setTextColor(Color.RED)
            btn_addOption.getBackground().setAlpha(0)
            btn_addOption.setTextSize(3, 8.5F)
            btn_addOption.setLayoutParams(LinearLayout.LayoutParams(75, 75))

            contenedorCreateOpciones.addView(listaOpciones)
            contenedorCreateOpciones.addView(btn_addOption)
            containerPrincipal.addView(contenedorCreateOpciones)

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
                //btnDeleteOpcion.setText("-")
                btnDeleteOpcion.setTextSize(3, 8.5F)
                btnDeleteOpcion.setLayoutParams(LinearLayout.LayoutParams(75, 75))
                btnDeleteOpcion.getBackground().setAlpha(0);
                btnDeleteOpcion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_remove_circle_outline_24, 0, 0, 0);
                btnDeleteOpcion.setTextColor(Color.RED);

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

            containerPrincipal.addView(contenedorOpciones)
            pregunt.addView(containerPrincipal)
            binding.listaPreguntas.addView(pregunt)
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
