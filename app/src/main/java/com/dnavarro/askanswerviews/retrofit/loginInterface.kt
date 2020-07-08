package com.dnavarro.askanswerviews.retrofit
import com.dnavarro.askanswerviews.entity.loginData
import com.dnavarro.askanswerviews.entity.loginResponse
import com.dnavarro.askanswerviews.entity.loginResponseUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface loginInterface {

    @POST("login")
    fun getLoginResponse(@Body loginData: loginData) : Call<loginResponseUser>

    @GET("logout")
    fun closeSession() : Call<loginResponse>

}