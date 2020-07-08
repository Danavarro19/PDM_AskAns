package com.dnavarro.askanswerviews.entity

data class lanzamientosResponse (var correct: Boolean,
                                 var lanzamientos: MutableCollection<lanzamientos>,
                                 var n: Int
)