package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.deleteRequest
import com.dnavarro.askanswerviews.entity.loginData
import com.dnavarro.askanswerviews.entity.loginResponse
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import retrofit2.http.POST

interface deleteEncuestaInterface {
    @HTTP(method = "DELETE", path = "encuestas/delete", hasBody = true)
    fun deleteEncuesta(@Body body: deleteRequest) : Call<loginResponse>
}