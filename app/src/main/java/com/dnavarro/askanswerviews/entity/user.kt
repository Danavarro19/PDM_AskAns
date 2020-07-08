package com.dnavarro.askanswerviews.entity

import java.util.*

data class user (
    var numid: String,
    var nombre: String,
    var apellido: String,
    var peso: Number,
    var nacimiento: String,
    var altura: Number,
    var pais: String,
    var ciudad: String,
    var sexo: String,
    var email: String,
    var tags: MutableCollection<String>,
    var saldo: Number
)