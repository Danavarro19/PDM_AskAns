package com.dnavarro.askanswerviews.fragments

import android.R.attr.button
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
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
import com.dnavarro.askanswerviews.entity.encuesta
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel


/**
 * A simple [Fragment] subclass.
 */
class Fragment_home : Fragment() {

    private val userModel: Userviewmodel by activityViewModels()

    @SuppressLint("ResourceAsColor", "ResourceType", "NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //this.activity?.setTitle(R.string.app_name)-->cambiar nombre de fragmento

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        userModel.resetUpdateOrCreate()
        userModel.updateEncuestas()
        userModel.resetListoParaEnviar()
        userModel.pass.observe(viewLifecycleOwner, Observer {
            if(it){
                println("es verdadero")

            }else{
                println("no es verdadero" + it)
                this.findNavController().navigate(R.id.frontPage)
            }
        })

        userModel.listoParaEnviar.observe(viewLifecycleOwner, Observer {
            if(it){
                println("listo para enviar: ")
                this.findNavController().navigate(R.id.action_fragment_home_to_fragmentResponder)
            }else{

            }
        })

        binding.bnvMenu.setOnNavigationItemReselectedListener {

            if(it.itemId == R.id.navEncuestaFragm){
                this.findNavController().navigate(R.id.action_fragment_home_to_fragmentSearchEncuestas)
            }
        }

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
                    newCarEncuesta.setContentPadding(0,0,0,15)
                    //newCarEncuesta.setLayoutParams(RelativeLayout.LayoutParams(400, 250))
                    newCarEncuesta.getBackground().setAlpha(51); //-->transparente cardview
                    newCarEncuesta.setOnClickListener {
                        //agregar codigo para ir a ver encuesta
                        // ver en tanto Hacer las respuestas en tiempo real
                    }

                    var titulo = TextView(this.context)
                    titulo.setPadding(20,20,0,3)
                    var tit = "Título: "
                    titulo.setTextColor(Color.rgb(0,0,0))
                    titulo.setTextSize(3, 7.5F)
                    titulo.setText(tit + encu.nombre_encuesta)

                    var descripcion = TextView(this.context)
                    descripcion.setPadding(20,0,0,5)
                    var descr = "Descripción: "
                    descripcion.setText(descr + encu.descrip_encuesta)

                    container.addView(titulo)
                    container.addView(descripcion)

                    var containerActions = LinearLayout(this.context)
                    containerActions.orientation = LinearLayout.HORIZONTAL

                    var btn_eliminar = Button(this.context)
                    //btn_eliminar.setText("Borrar")
                    //btn_eliminar.setBackgroundColor(Color.rgb(192,224,231))
                    btn_eliminar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_delete_24, 0, 0, 0);
                    btn_eliminar.setTextColor(Color.BLACK);
                    btn_eliminar.setLayoutParams(LinearLayout.LayoutParams(75, 75))


                    var btn_editar = Button(this.context)
                    //btn_editar.setText("Editar")
                    btn_editar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_edit_24, 0, 0, 0);
                    btn_editar.setTextColor(Color.BLACK);
                    btn_editar.setLayoutParams(LinearLayout.LayoutParams(75, 75))

                    var btn_descarga = Button(this.context)
                    //btn_descarga.setText("Descarga")
                    btn_descarga.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_save_alt_24, 0, 0, 0);
                    btn_descarga.setTextColor(Color.BLACK);
                    btn_descarga.setLayoutParams(LinearLayout.LayoutParams(75, 75))

                    var btn_contestar = Button(this.context)
                    btn_contestar.setOnClickListener {v->
                        userModel.getEncuestaToResolve(encu._id)
                    }
                    btn_contestar.setText("Contestar")
                    btn_contestar.setTextSize(3, 7.0F)
                    btn_contestar.setLayoutParams(LinearLayout.LayoutParams(160, 75))

                    var btn_copiarUrl = Button(this.context)
                    btn_copiarUrl.setText("Compartir")

                    btn_copiarUrl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_share_24, 0, 0, 0);
                    btn_copiarUrl.setTextColor(Color.BLACK);
                    btn_copiarUrl.setLayoutParams(LinearLayout.LayoutParams(75, 75))
                    //btn_copiarUrl.setPadding(0,0,0,50)

                    btn_copiarUrl.setOnClickListener {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Copia el siguiente Código: " + encu._id + "\nA&A App")
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }

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
                    containerActions.addView(btn_descarga)
                    containerActions.addView(btn_copiarUrl)
                    containerActions.addView(btn_contestar)

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
