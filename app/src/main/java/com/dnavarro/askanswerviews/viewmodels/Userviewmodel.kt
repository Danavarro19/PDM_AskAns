package com.dnavarro.askanswerviews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnavarro.askanswerviews.repository.UserRepository
import java.lang.Exception
class Userviewmodel: ViewModel() {
    private val userRepo = UserRepository()
    private val _mail = MutableLiveData<String>()
    val pass: LiveData<Boolean> = userRepo.pass
    val mail: LiveData<String> get() = _mail
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password;
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> get() = _msg

    init {
        _mail.value = ""
        _password.value = ""
        _msg.value = ""
    }

    fun register(){
        userRepo.registerSession()
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