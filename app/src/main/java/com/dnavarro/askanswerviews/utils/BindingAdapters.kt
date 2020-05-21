package com.dnavarro.askanswerviews.utils


import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:errorMsg")
fun errorMsg(view: TextView, msg: String){
    println("msg: "+msg)
    if(msg != ""){
        println("adapter-entro")
        view.error = msg
    }
}