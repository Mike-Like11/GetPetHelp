package com.mikelike.getpethelp.mobile.fragment

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mikelike.getpethelp.mobile.MainActivity
import com.mikelike.getpethelp.mobile.R
import com.mikelike.getpethelp.mobile.adapter.getAddress
import com.mikelike.getpethelp.mobile.databinding.FragmentCreateTaskBinding
import com.mikelike.getpethelp.mobile.databinding.FragmentFullTaskInfoBinding
import com.mikelike.getpethelp.mobile.domain.Task
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import com.mikelike.getpethelp.mobile.viewModel.FullTaskInfoViewModel
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import kotlinx.android.synthetic.main.fragment_create_task.*
import kotlinx.android.synthetic.main.short_task_item.view.*
import java.util.*


class FullTaskInfoFragment : Fragment(), OnMapReadyCallback {


    private lateinit var viewModel: FullTaskInfoViewModel
    private lateinit var task: Task
    private var _binding: FragmentFullTaskInfoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        task = requireArguments().getSerializable("task") as Task
        _binding = FragmentFullTaskInfoBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.fullTaskDesc.text = task.taskInfo?.description
        binding.fullTaskName.text = task.taskInfo?.name
        binding.fullTaskUserName.text = task.user?.firstName.toString().plus(" ").plus(task.user?.lastName.toString())
        binding.taskLocation.text = getAddress(LatLng(task.taskInfo?.latitude!!,
            task.taskInfo?.longitude!!
        ), requireContext()
        )
        val mapFragment: SupportMapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.uploadData.setOnClickListener {
                GetPetHelpApiImpl(context).addResponseTOTask(task.id)
            Toast.makeText(context,"Спасибо что откликнулись на данную заявку, заказчик свяжется с вами",Toast.LENGTH_LONG).show()
        }
        val imageLoader: ImageLoader =  ImageLoader.getInstance()
        imageLoader.displayImage(task.user?.avatarUrl, binding.taskAvatar)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FullTaskInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private lateinit var map: GoogleMap
    override fun onMapReady(p0: GoogleMap) {
        map = p0
        if(task.taskInfo!=null){
        map.addMarker(MarkerOptions().position(LatLng(task.taskInfo?.latitude!!,task.taskInfo?.longitude!!)).title(Geocoder(activity, Locale.getDefault()).getFromLocation(task.taskInfo?.latitude!!,task.taskInfo?.longitude!!,1).get(0).getAddressLine(0)))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(task.taskInfo?.latitude!!,task.taskInfo?.longitude!!), 10.0f))
        }
    }

}