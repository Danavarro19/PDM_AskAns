package com.dnavarro.askanswerviews.entity

data class respuestas( var usuario: String, var encuesta: String, var pagada: Boolean, var respuesta: MutableCollection<respuesta>, var fecha: String, var ip_disp: String)