package com.dnavarro.askanswerviews.retrofit


import com.dnavarro.askanswerviews.entity.sessionResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Header


interface sessionInterface {
    @GET("session")
    fun getsession() : Call<sessionResponse>
}