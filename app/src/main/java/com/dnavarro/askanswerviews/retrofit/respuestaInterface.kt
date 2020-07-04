package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.encuestaRequest
import com.dnavarro.askanswerviews.entity.loginResponse
import com.dnavarro.askanswerviews.entity.respuestas
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface respuestaInterface {
    @POST("encuestas/resolve")
    fun responder(@Body modelo: respuestas) : Call<loginResponse>
}