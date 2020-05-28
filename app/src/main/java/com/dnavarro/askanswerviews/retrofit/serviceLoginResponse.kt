package com.dnavarro.askanswerviews.retrofit

import android.app.Application
import android.content.Context
import com.dnavarro.askanswerviews.MainActivity
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore

object serviceLoginResponse {
//    USE YOUR URL CHANGE IT
    private const val URL: String = "http://192.168.1.8:3001/movil/"
    private lateinit var context: Application


    fun init(app: Application){
        this.context = app
        storeC = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this.context.applicationContext))
        intercep = HttpLoggingInterceptor()
        intercep.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder().addNetworkInterceptor(intercep).cookieJar(storeC).build()
        retrofit = Retrofit.Builder()

            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
//    private lateinit var cookie: CookieHandler

    private lateinit var storeC: ClearableCookieJar

    private lateinit var intercep: HttpLoggingInterceptor

    private lateinit var client: OkHttpClient






    private lateinit var retrofit: Retrofit


    fun<T> buildService(Service: Class<T>): T {
        return retrofit.create(Service)
    }
}