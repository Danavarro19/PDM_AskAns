package com.dnavarro.askanswerviews.entity

data class pregunta (var encabezado: String, var tipo: String,
var pregunta_abierta: Boolean, var multi_respuesta: Boolean, var requiere: Boolean,
var opciones: MutableCollection<opcion>)