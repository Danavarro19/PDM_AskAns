package com.dnavarro.askanswerviews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnavarro.askanswerviews.entity.encuesta
import com.dnavarro.askanswerviews.entity.pregunta

class CrearEncuestaViewModel:ViewModel() {

    private val _encuesta = MutableLiveData<encuesta>()
    val encuesta: LiveData<encuesta> get() = _encuesta


    fun init(encuesta: encuesta){
        _encuesta.value = encuesta
    }

    fun clean(){
        _encuesta.value = null
    }

    fun updateNombre(nombre: String){
        _encuesta.value!!.nombre_encuesta = nombre
    }

    fun update_descripcion(descr: String){
        _encuesta.value!!.descrip_encuesta = descr
    }

    fun getEncuesta(): encuesta{
        return _encuesta.value!!
    }



    fun addQuestion(pre: pregunta){
        _encuesta.value!!.preguntas.add(pre)
    }

    fun deleteQuestion(pre: pregunta){
        _encuesta.value!!.preguntas.remove(pre)
    }

    fun editQuestion(pre: pregunta, next: pregunta){
        _encuesta.value!!.preguntas.elementAt(_encuesta.value!!.preguntas.indexOf(pre)).apply {

            encabezado = next.encabezado
            tipo = next.tipo
            pregunta_abierta = next.pregunta_abierta
            multi_respuesta = next.multi_respuesta
            requiere = next.requiere
            opciones = next.opciones
        }
    }



}