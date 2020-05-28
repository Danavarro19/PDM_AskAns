package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.deleteRequest
import com.dnavarro.askanswerviews.entity.loginData
import com.dnavarro.askanswerviews.entity.loginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface deleteEncuestaInterface {
    @DELETE("encuestas/delete")
    fun deleteEncuesta(@Body body: deleteRequest) : Call<loginResponse>
}