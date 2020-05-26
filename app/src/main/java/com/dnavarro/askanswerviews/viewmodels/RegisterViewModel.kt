package com.dnavarro.askanswerviews.viewmodels

import androidx.annotation.FloatRange
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnavarro.askanswerviews.entity.registerBody
import com.dnavarro.askanswerviews.repository.UserRepository
import com.dnavarro.askanswerviews.utils.errorMsg
import org.intellij.lang.annotations.Pattern
import org.jetbrains.annotations.NotNull

class RegisterViewModel: ViewModel() {
    private val userRepo = UserRepository()
    val registerc: LiveData<Boolean> = userRepo.register
    private val _name = MutableLiveData<String>()

    val name: LiveData<String> get() = _name

    private val _lastname = MutableLiveData<String>()

    val lastname: LiveData<String> get() = _lastname

    private val _birtDate = MutableLiveData<String>()
    val birthDate: LiveData<String> get() = _birtDate

    private val _sex = MutableLiveData<String>()
    val sex: LiveData<String> get() = _sex

    private val _document = MutableLiveData<String>()
    val document: LiveData<String> get() = _document

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> get() = _country

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> get() = _city

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email


    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _tags = MutableLiveData<MutableCollection<String>>()
    val tags: LiveData<MutableCollection<String>> get() = _tags

    private val _altura = MutableLiveData<Float>()
    val altura: LiveData<Float> get() = _altura
    private val _peso = MutableLiveData<Float>()
    val peso: LiveData<Float> get() = _peso

    init {

        _name.value = ""
        _altura.value = 0f
        _lastname.value = ""
        _password.value = ""
        _email.value = ""
        _document.value = ""
        _peso.value = 0f
        _city.value = ""
        _sex.value = ""
        _tags.value = mutableListOf<String>()
    }

    fun register(){
        //todas las validaciones aqui

        var sendData: registerBody = registerBody(_name.value.toString(),_lastname.value.toString(),_birtDate.toString(),
        _sex.value.toString(),_document.value.toString(),_country.value.toString(),_city.value.toString(),_email.value.toString(),
        _password.value.toString(),_tags.value!!,_altura.value!!,_peso.value!!)
        userRepo.register(sendData)
    }

    fun updateName(name: String){
        _name.value = name
    }
    fun updateLastname(lastname: String){
        _lastname.value = lastname
    }

    fun updatebirthDate(fecha: String){
        _birtDate.value = fecha
    }

    fun updateSex(sex: String){
        _sex.value = sex
    }

    fun updateDocument(doc: String){
        _document.value = doc
    }

    fun updateCountry(pais: String){
        _country.value = pais
    }

    fun updateCity(city: String){
        _city.value = city
    }

    fun updateEmail(mail: String){
        _email.value = mail
    }

    fun updatePassword(pass: String){
        _password.value = pass
    }

    fun updateAltura(altura: Float){
        _altura.value = altura
    }

    fun updatePeso(peso: Float){
        _peso.value = peso
    }

    fun addTag(tag: String){
        if (!_tags.value!!.contains(tag)){
            _tags!!.value!!.add(tag)
        }
    }

    fun removeFromTag(tag: String){
        _tags.value!!.remove(tag)
    }




}