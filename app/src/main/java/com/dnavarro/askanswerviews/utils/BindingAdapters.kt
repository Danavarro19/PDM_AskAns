package com.dnavarro.askanswerviews.utils


import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.adapters.FrameLayoutBindingAdapter
import androidx.databinding.adapters.ViewGroupBindingAdapter
import com.dnavarro.askanswerviews.BR
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentRegisterBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup


@BindingAdapter("app:errorMsg")
fun errorMsg(view: TextView, msg: String){
    println("msg: $msg")
    if(msg != ""){
        println("adapter-entro")
        view.error = msg
    }
}

@BindingAdapter("app:List")
fun chips(view: ChipGroup, lista: MutableCollection<String>){
    println("create list of chips")
//    view.removeAllViews()
//    var inflater: LayoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var a = 0
    lista.forEach {
        println("tag: ".plus(it))
//        var binding = DataBindingUtil.inflate(inflater, R.layout.chip, view, true) as ViewDataBinding
//
//        binding.setVariable(BR.data,it)

//        var chip = inflater.inflate(R.layout.chip,null) as Chip
//        var chip = Chip(view.context)
//        var chip = Chip(view.context,null, R.style.Widget_MaterialComponents_Chip_Action)
//        var chip = ChipGroup.inflate(view.context,R.layout.chip,view) as Chip
//        var chip = LayoutInflater.from(view.context).inflate(R.layout.chip,null, false) as Chip
//        var chip = Chip.inflate(view.context,R.layout.chip,view) as Chip

//        chip.text = it
//        chip.isCloseIconVisible = true
//        chip.isClickable = false
//        chip.isCheckable = false

//        chip.layoutParams.width = ChipGroup.LayoutParams.WRAP_CONTENT
//        chip.layoutParams.height = ChipGroup.LayoutParams.WRAP_CONTENT
//        chip.isClickable = true
//        chip.isCheckable = true
//        chip.closeIcon = ContextCompat.getDrawable(view.context,R.drawable.ic_close_xhdpi_background)
//        view.addView(chip)



        a++
    }
//    view.invalidate()
}