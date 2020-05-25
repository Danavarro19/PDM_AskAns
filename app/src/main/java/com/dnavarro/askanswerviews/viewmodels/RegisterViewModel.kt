package com.dnavarro.askanswerviews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnavarro.askanswerviews.repository.UserRepository

class RegisterViewModel: ViewModel() {
    private val userRepo = UserRepository()
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
    private val _tags = MutableLiveData<String>()
    val tags: LiveData<String> get() = _tags
    private val _altura = MutableLiveData<Float>()
    val altura: LiveData<Float> get() = _altura
    private val _peso = MutableLiveData<Float>()
    val peso: LiveData<Float> get() = _peso

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




}