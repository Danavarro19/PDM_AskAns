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
import com.dnavarro.askanswerviews.entity.registerBody
import com.dnavarro.askanswerviews.entity.registerResponse
import com.dnavarro.askanswerviews.retrofit.registerInterface

class UserRepository {
    private val _pass = MutableLiveData<Boolean>()
    val pass: LiveData<Boolean> get() =  _pass
    val _register = MutableLiveData<Boolean>()
    val register: LiveData<Boolean> get() = _register
    init { //implementar cookie session
        _pass.value = false
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

    fun register(regist: registerBody){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(registerInterface::class.java)
        val call  = request.getLoginResponse(regist)

        call.enqueue(object: Callback<registerResponse>{
            override fun onResponse(call: Call<registerResponse>, response: Response<registerResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.body()}")
                    result = response.body()!!.correct
                    _register.value = result
                    println("Result $result")
                }else{

                    result = false
                    _register.value = result
                }

            }
            override fun onFailure(call: Call<registerResponse>, t: Throwable) {
                println(t.message)
                result = false
            }

        })



    }

}