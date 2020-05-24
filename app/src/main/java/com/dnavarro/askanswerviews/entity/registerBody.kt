package com.dnavarro.askanswerviews.entity


    data class registerBody(
        val name : String,
        val lastName : String,
        val birthDate : String,
        val sex : String,
        val document : String,
        val country : String,
        val city : String,
        val email : String,
        val password : String,
        val tags : List<String>,
        val altura: Float,
        val peso: Float
    )
