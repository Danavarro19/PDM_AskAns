package com.dnavarro.askanswerviews.fragments

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentRegisterBinding
import com.dnavarro.askanswerviews.viewmodels.RegisterViewModel
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Register : Fragment() {

    private val registerModel: RegisterViewModel by activityViewModels()
    private val userModel: Userviewmodel by activityViewModels()

    private lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register, container, false
        )
        val text = "Ocurrió un error al registrarse. Verifique todos los campos."
        val duration = Toast.LENGTH_LONG

        val toastError = Toast.makeText(this.activity!!.applicationContext, text, duration)

        registerModel.registerc.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){

                println("Se registro")
                userModel.register()
                this.findNavController().navigate(R.id.fragment_home)
            }else{
                toastError.show()
                println("Hubo un error en el registro")

                /// manejar el error del registro antes con validaciones
            }
        } )

    binding.registerModel = registerModel
        binding.apply {

            fieldPassword.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updatePassword(p0.toString() )
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldEmail.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updateEmail(p0.toString() )
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldDocument.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updateDocument(p0.toString() )
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldName.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updateName(p0.toString())
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldLastname.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updateLastname(p0.toString())
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldBirthdate.setOnClickListener{
                showDateDialog()
            }

//            fieldBirthdate.addTextChangedListener(object: TextWatcher {
//                override fun afterTextChanged(p0: Editable) {
//                    registerModel!!.updatebirthDate(p0.toString())
//                }
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//            })

            fieldAltura.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    try {
                        registerModel!!.updateAltura(p0.toString().toFloat() ?: 0f )

                    }catch (e: Exception){
                        println(e)
                    }
                  }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldPeso.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    try {
                        registerModel!!.updatePeso(p0.toString().toFloat() ?: 0f )
                    }catch (e: Exception){
                        println(e)
                    }

                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            pickerCountry.setOnCountryChangeListener {
                registerModel!!.updateCountry(pickerCountry.textView_selectedCountry.text.toString() )
            }

//            fieldCountry.addTextChangedListener(object: TextWatcher {
//                override fun afterTextChanged(p0: Editable) {
//                    registerModel!!.updateCountry(p0.toString() )
//                }
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//            })

            fieldCity.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updateCity(p0.toString() )
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
            //aqui!!
//            fieldSex.setOnCheckedChangeListener { group, checkedId ->
//
//                registerModel!!.updateSex(radio.text.toString())
//            }
            radioptF.setOnClickListener {
                registerModel!!.updateSex(radioptF.text.toString())
            }

            radioptM.setOnClickListener {
                registerModel!!.updateSex(radioptM.text.toString())
            }
            radioptOtro.setOnClickListener {
                registerModel!!.updateSex(radioptOtro.text.toString())
            }




            //aqui!!
            addChipBtn.setOnClickListener(View.OnClickListener {
                registerModel!!.addTag(add_chip.text.toString())
                var chip = Chip(context)
                chip.text = add_chip.text.toString()
                chip.isCloseIconVisible = true
                chip.isClickable = false
                chip.isCheckable = false
                listTags.addView(chip)
                chip.setOnCloseIconClickListener {
                    listTags.removeView(chip)
                    registerModel!!.removeFromTag(chip.text.toString())
                }
                add_chip.setText("")
            })
            //aqui!!
            listTags.setOnCheckedChangeListener(ChipGroup.OnCheckedChangeListener { group, checkedId ->
                var chip: Chip = group.getChildAt(checkedId) as Chip
                registerModel!!.removeFromTag(chip.text.toString())
            })



            buttonCancel.setOnClickListener {view: View ->
                view.findNavController()
                    .navigate(R.id.action_register_to_frontPage)
            }
            //aqui!!
            buttonRegister.setOnClickListener { view : View ->
//                view.findNavController().navigate(R.id.action_register_to_fragment_home)
                //hace falta impplementar
                registerModel!!.register()
            }











            fieldPassword.transformationMethod = PasswordTransformationMethod()
            fieldConfirmpassword.transformationMethod = PasswordTransformationMethod()

        }

        return binding.root
    }

    private fun showDateDialog(){

        val mDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            binding.fieldBirthdate.text = "${day}/${month+1}/${year}"
            var fecha = Calendar.getInstance()
            fecha.set(year,month,day)
            registerModel!!.updatebirthDate(fecha.time.toString())
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



}
