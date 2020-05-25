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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
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
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Register : Fragment() {

    private val registerModel: RegisterViewModel by activityViewModels()

    private lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register, container, false
        )
        registerModel.register.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){
                this.findNavController().navigate(R.id.fragment_home)
            }else{
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

            fieldBirthdate.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updatebirthDate(p0.toString())
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldAltura.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updateAltura(p0.toString().toFloat() ?: 0f )
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldPeso.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updatePeso(p0.toString().toFloat() ?: 0f )
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldCountry.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updateCountry(p0.toString() )
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldCity.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable) {
                    registerModel!!.updateCity(p0.toString() )
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            fieldSex.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                var radio: RadioButton = group.getChildAt(checkedId) as RadioButton
                registerModel.updateSex(radio.text.toString())
            })


            add_chip_btn.setOnClickListener(View.OnClickListener {
                registerModel.addTag(add_chip.text.toString())
                add_chip.setText("")
            })

            list_tags.setOnCheckedChangeListener(ChipGroup.OnCheckedChangeListener { group, checkedId ->
                var chip: Chip = group.getChildAt(checkedId) as Chip
                registerModel.removeFromTag(chip.text.toString())
            })



            buttonCancel.setOnClickListener {view: View ->
                view.findNavController()
                    .navigate(R.id.action_register_to_frontPage)
            }

            buttonRegister.setOnClickListener { view : View ->
//                view.findNavController().navigate(R.id.action_register_to_fragment_home)
                //hace falta impplementar

            }











            fieldPassword.transformationMethod = PasswordTransformationMethod()
            fieldConfirmpassword.transformationMethod = PasswordTransformationMethod()

        }

        return binding.root
    }

    private fun showDateDialog(){

        val mDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            binding.fieldBirthdate.text = "${day}/${month+1}/${year}"
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
