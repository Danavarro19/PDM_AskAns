package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.deleteRequest
import com.dnavarro.askanswerviews.entity.loginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HTTP

interface deleteLanzamiento {
    @HTTP(method = "DELETE", path = "lanzamientos/delete", hasBody = true)
    fun delete(@Body body: deleteRequest) : Call<loginResponse>
}