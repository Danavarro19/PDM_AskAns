package com.dnavarro.askanswerviews.retrofit
import com.dnavarro.askanswerviews.entity.loginData
import com.dnavarro.askanswerviews.entity.loginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface loginInterface {

    @POST("login")
    fun getLoginResponse(@Body loginData: loginData) : Call<loginResponse>
}