package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.encuestasResponse
import com.dnavarro.askanswerviews.entity.lanzamientosResponse
import retrofit2.Call
import retrofit2.http.GET

interface lanzamientoInterface {
    @GET("lanzamientos")
    fun get() : Call<lanzamientosResponse>
}