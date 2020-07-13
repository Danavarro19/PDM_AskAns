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

    private val _user = MutableLiveData<user>()
    val usuario: LiveData<user> get() = _user

    private val _sesioncerrada = MutableLiveData<Boolean>()
    val sesionclosed: LiveData<Boolean> get() = _sesioncerrada

    private val _lanzamientocreado = MutableLiveData<Boolean>()
    val lanzamientocreado: LiveData<Boolean> get() = _lanzamientocreado

    private val _lanzamientos = MutableLiveData<MutableCollection<lanzamientos>>()
    val lanza: LiveData<MutableCollection<lanzamientos>> get() = _lanzamientos

    private val _lanzamientosObtenidos = MutableLiveData<Boolean>()
    val lanzamientoObtenidos: LiveData<Boolean> get() = _lanzamientosObtenidos

    private val _CreadoOActualizado = MutableLiveData<Boolean>()
    val CreadoOActualizado: LiveData<Boolean> get() = _CreadoOActualizado

    private val _respuestaEnviada = MutableLiveData<Boolean>()
    val respuestaEnviada: LiveData<Boolean> get() = _respuestaEnviada

    private val _listoParaResponder = MutableLiveData<Boolean>()
    val listoParaResponder: LiveData<Boolean> get() = _listoParaResponder

    //encuestas
    private val _encuestas = MutableLiveData<MutableCollection<encuesta>>()
    val encuestas: LiveData<MutableCollection<encuesta>> get() = _encuestas

    private val _encuestasToResolve = MutableLiveData<encuesta>()
    val encuestaToResolve: LiveData<encuesta> get() = _encuestasToResolve

    private val _pass = MutableLiveData<Boolean>()
    //validacion
    val pass: LiveData<Boolean> get() =  _pass
    val _register = MutableLiveData<Boolean>()
    val register: LiveData<Boolean> get() = _register
    init { //implementar cookie session

        _user.value = user("","","",0,"",0,"","","","", mutableListOf(),0)
        session()
        _listoParaResponder.value = false
        _pass.value = false
        _register.value = false
        _encuestas.value = mutableSetOf<encuesta>()

    }

    fun resetCreateUpdate(){
        _CreadoOActualizado.value = false
    }

    fun reseteEncuestaenviada(){
        _respuestaEnviada.value = false
    }

    fun resetListoParaEnviar(){
        _listoParaResponder.value = false
    }

    fun resetLanzamientos(){
        _lanzamientosObtenidos.value = false
    }

    fun resetLanzamientoCreado(){
        _lanzamientocreado.value = false
    }

    fun session(){
        val request = serviceLoginResponse.buildService(sessionInterface::class.java)

        val call  = request.getsession()

        var result: Boolean = false
        call.enqueue(object: Callback<sessionResponse>{
            override fun onResponse(call: Call<sessionResponse>, response: Response<sessionResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.body()}")
                    result = response.body()!!.session
                    _pass.value = result
                    _sesioncerrada.value = false
                    _user.value = response.body()!!.usuario
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

    fun closeSession(){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(loginInterface::class.java)
        val call  = request.closeSession()

        call.enqueue(object: Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.correct){
                        _sesioncerrada.value = true
                        _pass.value = false
                    }
                }else{


                }

            }
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                println(t.message)
                result = false
            }

        })
    }

    fun Login(mail: String, password: String){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(loginInterface::class.java)
        val call  = request.getLoginResponse(loginData(mail,password))

        call.enqueue(object: Callback<loginResponseUser>{
            override fun onResponse(call: Call<loginResponseUser>, response: Response<loginResponseUser>) {
                if(response.isSuccessful){
                    println("Result ${response.headers()}")
                    result = response.body()!!.correct
                    _pass.value = result
                    _user.value =  response.body()!!.usuario
                    _sesioncerrada.value = false
                }else{

                    result = false
                    _pass.value = result
                }

            }
            override fun onFailure(call: Call<loginResponseUser>, t: Throwable) {
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
                        _user.value = response.body()!!.usuario
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

    fun getLanzamientos(){
        val request = serviceLoginResponse.buildService(lanzamientoInterface::class.java)
        val call  = request.get()
        var result: Boolean = false
        call.enqueue(object: Callback<lanzamientosResponse>{
            override fun onResponse(call: Call<lanzamientosResponse>, response: Response<lanzamientosResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.body()}")
                    result = response.body()!!.correct

                    if(result){
                        _lanzamientos.value = response.body()!!.lanzamientos
                        _lanzamientosObtenidos.value = true

                    }
                    println("Result $result")
                }else{
                    _lanzamientosObtenidos.value = false
                    result = false

                }

            }
            override fun onFailure(call: Call<lanzamientosResponse>, t: Throwable) {
                println(t.message)
                result = false
                _lanzamientosObtenidos.value = false
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
                            println("encuestas: " + encuestas.value)

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

    fun getEncuestaToResolve(id: String){
        val request = serviceLoginResponse.buildService(encuestaToresponseInterface::class.java)
        val call = request.getEncuestaToResolve(id)
        var result: Boolean = false
        call.enqueue(object: Callback<encuestasResponse>{
            override fun onResponse(call: Call<encuestasResponse>, response: Response<encuestasResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.body()}")
                    result = response.body()!!.correct

                    if(result){
                        _listoParaResponder.value = true
                       _encuestasToResolve.value = response.body()!!.encuestas.first()
                        println("encuesta a resolver: " + encuestaToResolve.value)

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

    fun makeAnAnswer(respuestas: respuestas){
        val request = serviceLoginResponse.buildService(respuestaInterface::class.java)
        val call = request.responder(respuestas)
        var result: Boolean = false
        call.enqueue(object: Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.body()}")
                    result = response.body()!!.correct

                    if(result){
                        _respuestaEnviada.value = result
                        println("se respondio la encuesta ")

                    }
                    println("Result $result")
                }else{

                    result = false
                    _respuestaEnviada.value = result
                }

            }
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                println(t.message)
                result = false
            }

        })
    }

    fun createLanzamiento(lanzamient: lanzamientos){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(crearLanzamientoInterface::class.java)
        val call  = request.crearOActualizar(lanzamient)

        call.enqueue(object: Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.headers()}")
                    result = response.body()!!.correct
                    getLanzamientos()
                    _lanzamientocreado.value = true
                    println("Result $result")
                }else{
                    _lanzamientocreado.value = false
                    result = false

                }

            }
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                println(t.message)
                result = false
                _lanzamientocreado.value = false
            }

        })
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

    fun deleteLanzamiento(id: String){
        var result: Boolean = false
        val request = serviceLoginResponse.buildService(deleteLanzamiento::class.java)
        val call  = request.delete(deleteRequest(id))
        call.enqueue(object: Callback<loginResponse>{
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                if(response.isSuccessful){
                    println("Result ${response.headers()}")
                    result = response.body()!!.correct
                    getLanzamientos()
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