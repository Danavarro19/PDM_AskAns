package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.encuesta
import com.dnavarro.askanswerviews.entity.encuestaRequest
import com.dnavarro.askanswerviews.entity.loginData
import com.dnavarro.askanswerviews.entity.loginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface crearEncuestaInterface {
    @POST("encuesta/createorupdate")
    fun crearOActualizar(@Body modelo: encuestaRequest) : Call<loginResponse>
}