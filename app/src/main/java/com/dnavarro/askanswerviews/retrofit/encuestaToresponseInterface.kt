package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.encuestasResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface encuestaToresponseInterface {
    @GET("encuestas/resolve")
    fun getEncuestaToResolve(@Query("encuestaid") id: String ) : Call<encuestasResponse>
}