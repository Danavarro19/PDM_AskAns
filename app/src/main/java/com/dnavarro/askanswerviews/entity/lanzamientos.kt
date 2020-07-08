package com.dnavarro.askanswerviews.entity

data class lanzamientos(
    var _id: String,
    var usuario: String,
    var encuesta: String,
    var cantidad_usuario: Int,
    var tags_publico: MutableCollection<String>,
    var pagada: Boolean,
    var cantidad_respuesta: Int,
    var encuesta_terminada: Boolean,
    var costo: Number
)