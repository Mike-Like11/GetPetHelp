package com.mikelike.getpethelp.mobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import com.mikelike.getpethelp.mobile.R
import com.mikelike.getpethelp.mobile.databinding.FragmentFullTaskInfoBinding
import com.mikelike.getpethelp.mobile.databinding.FragmentFullWorkerInfoBinding
import com.mikelike.getpethelp.mobile.domain.Task
import com.mikelike.getpethelp.mobile.domain.Worker
import com.nostra13.universalimageloader.core.ImageLoader

class FullWorkerInfoFragment : Fragment() {
    private lateinit var worker: Worker
    private var _binding: FragmentFullWorkerInfoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        worker = requireArguments().getSerializable("worker") as Worker
        _binding = FragmentFullWorkerInfoBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.fullWorkerExperience.text = worker.workerInfo?.experience
        binding.fullWorkerPreferences.text = worker.workerInfo?.preferences
        binding.fullWorkerUserName.text = worker.shortUserInfo?.firstName.toString().plus(" ").plus(worker.shortUserInfo?.lastName.toString())
        val imageLoader: ImageLoader =  ImageLoader.getInstance()
        imageLoader.displayImage(worker.shortUserInfo?.avatarUrl, binding.taskAvatar)
        return view
    }
}