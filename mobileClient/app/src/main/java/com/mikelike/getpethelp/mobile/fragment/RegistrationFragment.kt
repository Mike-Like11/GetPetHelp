package com.mikelike.getpethelp.mobile.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mikelike.getpethelp.mobile.R
import com.mikelike.getpethelp.mobile.databinding.FragmentRegistrationBinding
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import com.mikelike.getpethelp.mobile.viewModel.RegitsrationViewModel
import kotlinx.android.synthetic.main.fragment_registration.*


class RegistrationFragment : Fragment() {
    var SELECT_PICTURE = 200
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val vm = ViewModelProvider(this).get(RegitsrationViewModel::class.java)
        _binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.SelectImage.setOnClickListener {
            openGalleryForImage()
        }
        binding.regPhone.doAfterTextChanged {
            vm.phone.value = it.toString()
        }
        binding.regFirstname.doAfterTextChanged {
            vm.firstName.value = it.toString()
        }
        binding.regLastname.doAfterTextChanged {
            vm.lastName.value = it.toString()
        }
        binding.regMiddlename.doAfterTextChanged {
            vm.middleName.value = it.toString()
        }
        binding.regCity.doAfterTextChanged {
            vm.city.value = it.toString()
        }
        binding.regAge.doAfterTextChanged {
            vm.age.value = it.toString().toInt()
        }
        binding.regPassword.doAfterTextChanged {
            vm.password.value = it.toString()
        }
        binding.regEmail.doAfterTextChanged {
            vm.email.value = it.toString()
        }
        binding.textQuestion.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,LoginFragment())
                commit()
            }
        }
        binding.createAccount.setOnClickListener{
            if(binding.PreviewImage.isVisible) {
                vm.avatar.value = binding.PreviewImage.drawable?.toBitmap()
            }
            else{
                vm.avatar.value = null
            }
            vm.createAccount()
            GetPetHelpApiImpl(activity).registration(vm.getUser().value!!)
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,LoginFragment())
                commit()
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_PICTURE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE){
            binding.PreviewImage.setImageURI(data?.data) // handle chosen image
            binding.PreviewImage.visibility = View.VISIBLE
        }
    }
}