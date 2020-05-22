package com.dnavarro.askanswerviews.repository

import android.util.MutableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnavarro.askanswerviews.entity.loginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import com.dnavarro.askanswerviews.retrofit.loginInterface
import com.dnavarro.askanswerviews.retrofit.serviceLoginResponse
import com.dnavarro.askanswerviews.entity.loginResponse
class UserRepository {
    private val _pass = MutableLiveData<Boolean>()
    val  pass: LiveData<Boolean> get() =  _pass
    init { //implementar cookie session
    }
    fun Login(mail: String, password: String){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(loginInterface::class.java)
        val call  = request.getLoginResponse(loginData(mail,password))

        call.enqueue(object: Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.body()}")
                    result = response.body()!!.correct
                    _pass.value = result
                    println("Result $result")
                }else{

                    result = false
                    _pass.value = result
                }

            }
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                println(t.message)
                result = false
            }

        })



    }

}