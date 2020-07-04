package com.dnavarro.askanswerviews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnavarro.askanswerviews.entity.encuesta
import com.dnavarro.askanswerviews.entity.respuestas
import com.dnavarro.askanswerviews.repository.UserRepository
import java.lang.Exception
class Userviewmodel: ViewModel() {
    private val userRepo = UserRepository()

    val UpdateOrCreated = userRepo.CreadoOActualizado
    val listaEncuesta: LiveData<MutableCollection<encuesta>> = userRepo.encuestas
    val respuestaEnviada: LiveData<Boolean> = userRepo.respuestaEnviada
    val encuestaToResolve: LiveData<encuesta> = userRepo.encuestaToResolve
    val listoParaEnviar: LiveData<Boolean> = userRepo.listoParaResponder

    private val _mail = MutableLiveData<String>()
    val pass: LiveData<Boolean> = userRepo.pass
    val mail: LiveData<String> get() = _mail
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password;
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> get() = _msg


    private val _estado = MutableLiveData<String>()
    val estado: LiveData<String> get()  = _estado

    private val _encuestaToUpdate = MutableLiveData<encuesta>()
    val encuestaToUpate: LiveData<encuesta> get() = _encuestaToUpdate

    fun Edit(encuesta: encuesta){
        _encuestaToUpdate.value = encuesta;
    }
    fun Create(){
        _encuestaToUpdate.value = encuesta("","","","",
         mutableListOf())
    }
    fun Clear(){
        _encuestaToUpdate.value = null
    }

    fun deleteEncuesta(encuesta: encuesta){
        userRepo.deleteEncuesta(encuesta)
    }

    fun resetListoParaEnviar(){
        userRepo.resetListoParaEnviar()
    }

    fun getEncuestaToResolve(id: String){
        userRepo.getEncuestaToResolve(id)
    }

    init {
        _mail.value = ""
        _password.value = ""
        _msg.value = ""
        _estado.value = "None"
    }

    fun resetEnviarRespuesta(){
        userRepo.reseteEncuestaenviada()
    }

    fun makeAnswer(resp: respuestas){
        userRepo.makeAnAnswer(resp)
    }

    fun createOrUpdate(encuesta: encuesta){
        userRepo.actualizarOCrearEncuesta(encuesta)
    }

    fun resetUpdateOrCreate(){
        userRepo.resetCreateUpdate()
    }

    fun ToCreate(){
        _estado.value ="Create"
    }
    fun ToClearState(){
        _estado.value = "None"
    }
    fun ToUpdateState(){
        _estado.value = "Update"
    }

    fun register(){
        userRepo.registerSession()
    }
    fun updateEncuestas(){
        userRepo.getEncuestas()
    }
    fun checkPassword(){
        try {
            println("mail: ${_mail.value.toString()} password: ${_password.value.toString()}")
            if (_mail.value.toString() == "" || _password.toString() == ""){
                    _msg.value = "Los datos no puedes estar vacios."
            }else{
                userRepo.Login(_mail.value.toString(),_password.value.toString())
            }
        }catch (e: Exception){
            println(e.message)
        }
    }
    fun changePassword(password: String?){
        _password.value = password
    }
    fun changeUsername(mail: String?){
        _mail.value = mail
    }
    fun changeMessage(msg: String){
        _msg.value = msg
    }


}