package com.mikelike.getpethelp.mobile.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mikelike.getpethelp.mobile.R
import com.mikelike.getpethelp.mobile.databinding.FragmentCreateTaskBinding
import com.mikelike.getpethelp.mobile.viewModel.CreateTaskViewModel
import java.util.*

class CreateTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, OnMapReadyCallback {
    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    companion object {
        fun newInstance() = CreateTaskFragment()
        private const val REQUEST_LOCATION = 1 //request code to identify specific permission request
        private const val TAG = "MapsActivity"
    }

    var MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    var locationPermissionGranted:Boolean =false

    private lateinit var viewModel: CreateTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateTaskBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        val mapFragment:SupportMapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupLocClient()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().title = "Создание задания"
        binding.taskName.doAfterTextChanged {
            viewModel.name.postValue(it.toString())
        }
        binding.taskDesc.doAfterTextChanged {
            viewModel.description.postValue(it.toString())
        }
        binding.btnPick.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(requireActivity(), this, year, month,day)
            datePickerDialog.show()
        }
        binding.createTask.setOnClickListener {
            viewModel.createTask()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateTaskViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(requireActivity(), this, hour, minute,
            DateFormat.is24HourFormat(requireActivity()))
        timePickerDialog.show()
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        val pickedDateTime = Calendar.getInstance()
        pickedDateTime.set(myYear, myMonth, myDay, myHour, myMinute, 0)
        binding.taskTime.setText(pickedDateTime.time.toLocaleString())
        viewModel.dateOfPerformance.postValue(pickedDateTime.time.toString())
    }
    private lateinit var map: GoogleMap
    private lateinit var fusedLocClient: FusedLocationProviderClient
    override fun onMapReady(p0: GoogleMap) {
        map = p0
        getCurrentLocation()
        map.setOnMapClickListener {
            map.clear()
            map.addMarker(MarkerOptions().position(it).title(Geocoder(activity, Locale.getDefault()).getFromLocation(it.latitude,it.longitude,1).get(0).getAddressLine(0)))
            val update = CameraUpdateFactory.newLatLngZoom(it, 10.0f)
            viewModel.latitude.value = it.latitude
            viewModel.longitude.value = it.longitude
            map.moveCamera(update)
    }

            //val geocoder: Geocoder
            //geocoder = Geocoder(activity, Locale.getDefault())
            //desc.setText(geocoder.getFromLocation(it.latitude,it.longitude,1).get(0).getAddressLine(0))
    }
    private fun setupLocClient() {
        fusedLocClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    // prompt the user to grant/deny access
    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            1)
    }

    private fun getCurrentLocation() {
        // Check if the ACCESS_FINE_LOCATION permission was granted before requesting a location
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            // If the permission has not been granted, then requestLocationPermissions() is called.
            requestLocPermissions()
        } else {

            fusedLocClient.lastLocation.addOnCompleteListener {
                // lastLocation is a task running in the background
                val location = it.result //obtain location
                //Get a reference to the database, so your app can perform read and write operations
                if (location != null) {

                    val latLng = LatLng(location.latitude, location.longitude)
                    // create a marker at the exact location
                    map.addMarker(MarkerOptions().position(latLng)
                        .title("You are currently here!"))
                    // create an object that will specify how the camera will be updated
                    viewModel.latitude.value = latLng.latitude
                    viewModel.longitude.value = latLng.longitude
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 10.0f)

                    map.moveCamera(update)
                    //Save the location data to the database
                } else {
                    // if location is null , log an error message
                    Log.e(TAG, "No location found")
                }
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        //check if the request code matches the REQUEST_LOCATION
        if (requestCode == 1)
        {
            //check if grantResults contains PERMISSION_GRANTED.If it does, call getCurrentLocation()
            if (grantResults.size == 1 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                Log.d("fdsfsdf", "Location permission denied2")
                getCurrentLocation()
            } else {
                //if it doesn`t log an error message
                Log.e("adsadsa", "Location permission denied")
            }
        }
    }
}