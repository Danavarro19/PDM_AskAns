package com.dnavarro.askanswerviews.entity

data class encuesta (
    var usuario: String,
    var nombre_encuesta: String,
    var descrip_encuesta: String,
    var ip_disp: String,
    var lanzamiento_pago: MutableCollection<String>,
    var preguntas: MutableCollection<pregunta>
)