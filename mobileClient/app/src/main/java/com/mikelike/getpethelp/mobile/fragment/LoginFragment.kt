package com.mikelike.getpethelp.mobile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikelike.getpethelp.mobile.R
import com.mikelike.getpethelp.mobile.databinding.FragmentLoginBinding
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import com.mikelike.getpethelp.mobile.viewModel.CreateCVViewModel
import com.mikelike.getpethelp.mobile.viewModel.LoginViewModel
import com.mikelike.getpethelp.mobile.viewModel.MyProfileViewModel


class LoginFragment : Fragment() {
    private lateinit var viewModel: MyProfileViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.GONE
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        val vm = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.textQuestion.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,RegistrationFragment())
                commit()
            }
        }
        binding.loginPassword.doAfterTextChanged {
            vm.password.value = it.toString()
        }
        binding.loginEmail.doAfterTextChanged {
            vm.email.value = it.toString()
        }
        vm.errors.observeForever {
            if(it){
                binding.loginEmail.error = "Неправильная почта или пароль"
            }
            else{
                binding.loginEmail.error = null
            }
        }
        binding.startApp.setOnClickListener {
            vm.login()
            vm.loading.observeForever {
                vm.errors.observeForever {it2->
                    if(!it && !it2) {
                        requireActivity().supportFragmentManager.beginTransaction().apply {
                            replace(R.id.flFragment, MyProfileFragment())
                            commit()
                            navBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        return view
    }

}