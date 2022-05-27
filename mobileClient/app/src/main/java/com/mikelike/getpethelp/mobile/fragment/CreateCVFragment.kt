package com.mikelike.getpethelp.mobile.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.mikelike.getpethelp.mobile.R
import com.mikelike.getpethelp.mobile.databinding.FragmentCreateCVBinding
import com.mikelike.getpethelp.mobile.databinding.FragmentLoginBinding
import com.mikelike.getpethelp.mobile.viewModel.CreateCVViewModel

class CreateCVFragment : Fragment() {
    private var _binding: FragmentCreateCVBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateCVViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this).get(CreateCVViewModel::class.java)
        _binding = FragmentCreateCVBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.newWorkerExperience.doAfterTextChanged {
            viewModel.experience.value = it.toString()
        }
        binding.newWorkerPreferences.doAfterTextChanged {
            viewModel.preferences.value = it.toString()
        }
        binding.startApp.setOnClickListener {
            viewModel.createCV()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,MyProfileFragment())
                commit()
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }


}