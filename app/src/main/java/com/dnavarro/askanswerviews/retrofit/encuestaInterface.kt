package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.encuestasResponse
import com.dnavarro.askanswerviews.entity.sessionResponse
import retrofit2.Call
import retrofit2.http.GET

interface encuestaInterface {
    @GET("encuestas")
    fun getEncuestas() : Call<encuestasResponse>
}