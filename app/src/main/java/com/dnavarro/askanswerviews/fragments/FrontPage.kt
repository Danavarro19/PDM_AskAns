package com.dnavarro.askanswerviews.fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentFrontPageBinding
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class FrontPage : Fragment() {

    private val userModel: Userviewmodel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFrontPageBinding>(
            inflater,
            R.layout.fragment_front_page,container,false )



        val text = "El usuario o la contraseña son incorrectos."
        val duration = Toast.LENGTH_LONG
        val toastError = Toast.makeText(this.activity!!.applicationContext, text, duration)



        binding.userModel = userModel
        binding.lifecycleOwner = this
        userModel.pass.observe(this.viewLifecycleOwner, Observer {
            if(it){
                println("se logueo")
                this.findNavController().navigate(R.id.fragment_home)
//                definir navigation con logueo correcto
            }else{
                userModel.changeMessage("Datos incorrectos")
                toastError.show()
                println("no se pudo loguear")
            }
        })
        binding.apply {

            fieldPassword.transformationMethod = PasswordTransformationMethod()

            buttonSignin.setOnClickListener{
                userModel!!.changePassword(fieldPassword.text.toString())
                userModel!!.changeUsername(fieldEmail.text.toString())

               userModel!!.checkPassword()

            }


            buttonRegister.setOnClickListener { view: View ->
                view.findNavController().navigate(R.id.action_frontPage_to_register)
            }
        }



        return binding.root
    }

}
