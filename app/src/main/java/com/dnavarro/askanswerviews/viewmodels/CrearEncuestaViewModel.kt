package com.dnavarro.askanswerviews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnavarro.askanswerviews.entity.encuesta
import com.dnavarro.askanswerviews.entity.pregunta

class CrearEncuestaViewModel:ViewModel() {

    val encuesta = MutableLiveData<encuesta>()
    val encuestaT: LiveData<encuesta> get() = encuesta


    fun init(encuest: encuesta){
        encuesta.value = encuest
    }

    fun clean(){
        encuesta.value = null
    }

    fun updateNombre(nombre: String){
        encuesta.value!!.nombre_encuesta = nombre
    }

    fun update_descripcion(descr: String){
        encuesta.value!!.descrip_encuesta = descr
    }

    fun getEncuesta(): encuesta{
        return encuestaT.value!!
    }



    fun addQuestion(pre: pregunta){
        encuesta.value!!.preguntas.add(pre)
    }

    fun deleteQuestion(pre: pregunta){
        encuesta.value!!.preguntas.remove(pre)
    }

    fun editQuestion(pre: pregunta, next: pregunta){
        encuesta.value!!.preguntas.elementAt(encuesta.value!!.preguntas.indexOf(pre)).apply {

            encabezado = next.encabezado
            tipo = next.tipo
            pregunta_abierta = next.pregunta_abierta
            multi_respuesta = next.multi_respuesta
            requiere = next.requiere
            opciones = next.opciones
        }
    }



}