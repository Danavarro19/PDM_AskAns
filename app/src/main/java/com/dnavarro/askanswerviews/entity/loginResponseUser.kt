package com.dnavarro.askanswerviews.entity

data class loginResponseUser(
    var status: Int,
    var correct: Boolean,
    var usuario: user
)