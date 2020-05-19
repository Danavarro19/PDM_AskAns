package com.dnavarro.askanswerviews

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.dnavarro.askanswerviews.databinding.FragmentRegisterBinding
import com.google.gson.Gson
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Register : Fragment() {

    data class UserToRegister(
        val name : String,
        val lastName : String,
        val birthDate : String,
        val sex : String,
        val document : String,
        val country : String,
        val city : String,
        val email : String,
        val password : String,
        val preferences : List<String>
    )

    lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )

        binding.apply {
            fieldBirthdate.setOnClickListener{
                showDateDialog()
            }

            buttonCancel.setOnClickListener {view: View ->
                view.findNavController()
                    .navigate(RegisterDirections
                        .actionRegisterToFrontPage())
            }

            buttonRegister.setOnClickListener { view : View ->
                val user = UserToRegister(
                    fieldName.text.toString(),
                    fieldLastname.text.toString(),
                    fieldBirthdate.text.toString(),
                    "Sex",
                    fieldDocument.text.toString(),
                    "country",
                    fieldCity.text.toString(),
                    fieldEmail.text.toString(),
                    fieldPassword.text.toString(),
                    listOf()
                )
                val gson = Gson()
                val jsonUser = gson.toJson(user)
                Log.d("Register", jsonUser)

                view.findNavController()
                    .navigate(RegisterDirections
                        .actionRegisterToFrontPage())

            }

            spinnerPreferences.adapter = retrievePreferences()
            spinnerPreferences.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    //
                }

            }




            fieldPassword.transformationMethod = PasswordTransformationMethod()
            fieldConfirmpassword.transformationMethod = PasswordTransformationMethod()

        }

        return binding.root
    }

    fun showDateDialog(){

        val mDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val dateOfBirth = "${day}/${month+1}/${year}"
            binding.fieldBirthdate.text = dateOfBirth
        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
            this@Register.requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            mDateListener,
            year, month, day)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
    fun retrievePreferences() : ArrayAdapter<String>{
        return ArrayAdapter(
            this@Register.requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf("Opcion 1","Opcion 2","Opcion 3","Opcion 4")
        )

    }

}
