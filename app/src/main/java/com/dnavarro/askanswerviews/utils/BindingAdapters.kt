package com.dnavarro.askanswerviews.utils


import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.dnavarro.askanswerviews.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("app:errorMsg")
fun errorMsg(view: TextView, msg: String){
    println("msg: "+msg)
    if(msg != ""){
        println("adapter-entro")
        view.error = msg
    }
}

@BindingAdapter("app:List")
fun chips(view: ChipGroup, lista: MutableCollection<String>){
    println("create list of chips")
    view.removeAllViews()
    lista.forEach {
        var chip: Chip = Chip(view.context,null, R.style.Widget_MaterialComponents_Chip_Action)
        chip.text = it
        chip.closeIcon = ContextCompat.getDrawable(view.context,R.drawable.ic_close_xhdpi_background)
        view.addView(chip)
    }
}