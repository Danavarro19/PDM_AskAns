package com.dnavarro.askanswerviews.entity

data class encuestasResponse(var correct: Boolean, var encuestas: MutableCollection<encuesta>, var n: Int)