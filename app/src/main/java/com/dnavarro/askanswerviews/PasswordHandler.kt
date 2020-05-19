package com.dnavarro.askanswerviews

class PasswordHandler(val password: String) {

    fun isEqual(password: String): Boolean {
        return this.password == password
    }


}