package com.mikelike.getpethelp.mobile.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mikelike.getpethelp.mobile.R
import com.mikelike.getpethelp.mobile.adapter.TaskAdapter
import com.mikelike.getpethelp.mobile.adapter.TaskInfoProfileAdapter
import com.mikelike.getpethelp.mobile.databinding.MyProfileFragmentBinding
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import com.mikelike.getpethelp.mobile.viewModel.MyProfileViewModel
import com.mikelike.getpethelp.mobile.viewModel.MyProfileViewModelFactory
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MyProfileFragment()
    }
    private var _binding: MyProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MyProfileFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        viewModel = ViewModelProviders.of(
            this,
            MyProfileViewModelFactory(GetPetHelpApiImpl(activity),requireActivity().application)
        ).get(MyProfileViewModel::class.java)
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        viewModel.getUserInfo()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Мой профиль"
        val text: Spannable = SpannableString((requireActivity() as AppCompatActivity).supportActionBar?.title)
        text.setSpan(
            ForegroundColorSpan(Color.rgb(254,208,83)),
            0,
            text.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        viewModel.loading.observeForever {
            binding.container.isVisible  = it.not()
        }
        viewModel.user.observeForever {
            binding.currentAge.text = it.fullUserInfo.age.toString()
            binding.currentCity.text = it.fullUserInfo.city.toString()
            binding.currentEmail.text = it.email.toString()
            binding.currentName.text = it.shortUserInfo.firstName.toString().plus(" ").plus(it.shortUserInfo.lastName)
            val imageLoader: ImageLoader = ImageLoader.getInstance()
            imageLoader.displayImage(it.shortUserInfo.avatarUrl, binding.currentAvatar)
        }
        viewModel.myTasks.observeForever {
            binding.allMyTasks.adapter = TaskInfoProfileAdapter(requireActivity(),it)
        }
        binding.deleteAccount.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            val edit = sharedPreferences.edit()
            edit.clear()
            edit.apply()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,LoginFragment())
                commit()
            }
        }
        viewModel.worker.observeForever {
            binding.workerProfile.visibility = View.VISIBLE
            binding.workerExperience.text = it.workerInfo?.experience
            binding.workerPreferences.text = it.workerInfo?.preferences
        }
        binding.createCv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,CreateCVFragment())
                commit()
            }
        }
        binding.uploadData.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,CreateTaskFragment())
                commit()
            }
        }
        return view
    }


}
