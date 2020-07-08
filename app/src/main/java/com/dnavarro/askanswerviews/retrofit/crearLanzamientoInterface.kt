package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.encuestaRequest
import com.dnavarro.askanswerviews.entity.lanzamientos
import com.dnavarro.askanswerviews.entity.loginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface crearLanzamientoInterface {
    @POST("lanzamientos/createorupdate")
    fun crearOActualizar(@Body modelo: lanzamientos) : Call<loginResponse>
}