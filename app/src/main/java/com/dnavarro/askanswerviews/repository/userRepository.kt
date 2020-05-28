package com.dnavarro.askanswerviews.repository

import android.util.MutableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnavarro.askanswerviews.entity.*
import com.dnavarro.askanswerviews.retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val _CreadoOActualizado = MutableLiveData<Boolean>()
    val CreadoOActualizado: LiveData<Boolean> get() = _CreadoOActualizado

    //encuestas
    private val _encuestas = MutableLiveData<MutableCollection<encuesta>>()
    val encuestas: LiveData<MutableCollection<encuesta>> get() = _encuestas
    private val _pass = MutableLiveData<Boolean>()
    //validacion
    val pass: LiveData<Boolean> get() =  _pass
    val _register = MutableLiveData<Boolean>()
    val register: LiveData<Boolean> get() = _register
    init { //implementar cookie session

        session()
        _pass.value = false
        _register.value = false
        _encuestas.value = mutableSetOf<encuesta>()
    }

    fun resetCreateUpdate(){
        _CreadoOActualizado.value = false
    }


    fun session(){
        val request = serviceLoginResponse.buildService(sessionInterface::class.java)
        val call  = request.getsession()
        var result: Boolean = false
        call.enqueue(object: Callback<sessionResponse>{
            override fun onResponse(call: Call<sessionResponse>, response: Response<sessionResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.headers()}")
                    result = response.body()!!.session
                    _pass.value = result
                    println("Result $result")
                }else{
                    println("Result ${response.headers()}")
                    result = false
                    _pass.value = result
                }

            }
            override fun onFailure(call: Call<sessionResponse>, t: Throwable) {
                println("Fail " + t.message)
                result = false
            }

        })
    }

    fun Login(mail: String, password: String){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(loginInterface::class.java)
        val call  = request.getLoginResponse(loginData(mail,password))

        call.enqueue(object: Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.headers()}")
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
                    if(result){
                        _pass.value = true
                    }
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

    fun getEncuestas(){
        if(_pass.value!!){
            val request = serviceLoginResponse.buildService(encuestaInterface::class.java)
            val call  = request.getEncuestas()
            var result: Boolean = false
            call.enqueue(object: Callback<encuestasResponse>{
                override fun onResponse(call: Call<encuestasResponse>, response: Response<encuestasResponse>) {
                    if(response.isSuccessful){
                        println("Result ${response.body()}")
                        result = response.body()!!.correct

                        if(result){
                            _encuestas.value = response.body()!!.encuestas
                        }
                        println("Result $result")
                    }else{

                        result = false

                    }

                }
                override fun onFailure(call: Call<encuestasResponse>, t: Throwable) {
                    println(t.message)
                    result = false
                }

            })
        }
    }

    fun actualizarOCrearEncuesta(encuesta: encuesta){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(crearEncuestaInterface::class.java)
        val call  = request.crearOActualizar(encuestaRequest(encuesta))

        call.enqueue(object: Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.headers()}")
                    result = response.body()!!.correct
                    _CreadoOActualizado.value = result
                    getEncuestas()
                    println("Result $result")
                }else{

                    result = false
                    _CreadoOActualizado.value = result
                }

            }
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                println(t.message)
                result = false
            }

        })

    }

    fun registerSession(){
        _pass.value = true
    }

    fun deleteEncuesta(encuesta: encuesta){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(deleteEncuestaInterface::class.java)
        val call  = request.deleteEncuesta(deleteRequest(encuesta._id))

        call.enqueue(object: Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.headers()}")
                    result = response.body()!!.correct
                    getEncuestas()
                    println("Result $result")
                }else{

                    result = false

                }

            }
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                println(t.message)
                result = false
            }

        })
    }



}