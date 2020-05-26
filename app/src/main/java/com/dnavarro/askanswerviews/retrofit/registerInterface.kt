package com.dnavarro.askanswerviews.retrofit

import com.dnavarro.askanswerviews.entity.loginData
import com.dnavarro.askanswerviews.entity.loginResponse
import com.dnavarro.askanswerviews.entity.registerBody
import com.dnavarro.askanswerviews.entity.registerResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface registerInterface {
    @POST("register")
    fun getLoginResponse(@Body register: registerBody) : Call<registerResponse>
}