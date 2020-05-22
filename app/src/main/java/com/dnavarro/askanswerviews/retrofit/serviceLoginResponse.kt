package com.dnavarro.askanswerviews.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object serviceLoginResponse {
//    USE YOUR URL CHANGE IT
    private const val URL: String = "http://192.168.14.1:3001/movil/"
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    fun<T> buildService(Service: Class<T>): T {
        return retrofit.create(Service)
    }
}