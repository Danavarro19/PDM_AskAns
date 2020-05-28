package com.dnavarro.askanswerviews.entity

data class encuesta (
    var _id: String,
    var usuario: String,
    var nombre_encuesta: String,
    var descrip_encuesta: String,
    var preguntas: MutableCollection<pregunta>
)