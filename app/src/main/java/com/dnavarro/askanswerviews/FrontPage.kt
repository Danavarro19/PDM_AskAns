package com.dnavarro.askanswerviews

import android.os.Bundle
import android.text.Layout
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.dnavarro.askanswerviews.databinding.FragmentFrontPageBinding
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 */
class FrontPage : Fragment() {

    data class UserToSign(val email : String, val password: String)
    private lateinit var binding : FragmentFrontPageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_front_page,container,false )

        binding.apply {

            fieldPassword.transformationMethod = PasswordTransformationMethod()

            buttonSignin.setOnClickListener{
                val user = UserToSign(fieldEmail.text.toString(), fieldPassword.text.toString())
                val gson = Gson()
                val jsonUser = gson.toJson(user)
                Log.d("FrontPage", jsonUser)
            }


            buttonRegister.setOnClickListener { view: View ->
                view.findNavController()
                    .navigate(FrontPageDirections.actionFrontPageToRegister())
            }
        }



        return binding.root
    }

}
