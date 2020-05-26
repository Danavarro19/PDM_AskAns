package com.dnavarro.askanswerviews.entity


    data class registerBody(
        val nombre : String,
        val apellido : String,
        val nacimiento : String,
        val sexo : String,
        val numId : String,
        val pais : String,
        val ciudad : String,
        val email : String,
        val password : String,
        val tags : MutableCollection<String>,
        val altura: Float,
        val peso: Float
    )
