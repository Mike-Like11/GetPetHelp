package com.mikelike.getpethelp.mobile.adapter

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.mikelike.getpethelp.mobile.MainActivity
import com.mikelike.getpethelp.mobile.databinding.FragmentLoginBinding
import com.mikelike.getpethelp.mobile.databinding.ShortTaskItemBinding
import com.mikelike.getpethelp.mobile.domain.Task
import com.mikelike.getpethelp.mobile.fragment.FullTaskInfoFragment
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.short_task_item.view.*
import kotlinx.android.synthetic.main.tasks_fragment.*
import kotlinx.android.synthetic.main.tasks_fragment.view.*
import java.io.IOException

public fun getAddress(latLng: LatLng,context: Context): String {
    // 1
    val geocoder = Geocoder(context)
    val addresses: List<Address>?
    val address: Address?
    var addressText = ""

    try {
        // 2
        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        // 3
        Log.d("address",addresses.toString())
        if (null != addresses && !addresses.isEmpty()) {
            address = addresses[0]
            for (i in 0..address.maxAddressLineIndex) {
                addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                Log.d("address",addressText)
            }
        }
        Log.d("address",addressText)
    } catch (e: IOException) {
        Log.e("MapsActivity", e.localizedMessage)
    }

    return addressText
}
class TaskAdapter(context: Context, taskList: List<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater
    private val taskList: List<Task>
    private val context: Context

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ShortTaskItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val task = taskList[position]
        Log.d("qweq",task.toString())
        holder.itemView.task_name.text = task.taskInfo?.name.toString()
        val imageLoader: ImageLoader =  ImageLoader.getInstance()
        imageLoader.displayImage(task.user?.avatarUrl, holder.itemView.task_avatar)
        holder.itemView.task_date.text = task.taskInfo?.dateOfPerformance.toString()
        holder.itemView.task_user.text = task.user?.firstName.toString().plus(" ").plus(task.user?.lastName.toString())
        holder.itemView.task_location.text = getAddress(LatLng(task.taskInfo?.latitude!!,
            task.taskInfo.longitude!!
        ),context)
        holder.itemView.setOnClickListener{
            val fullTaskInfoFragment = FullTaskInfoFragment()
            val bundle = Bundle()
            bundle.putSerializable("task", task)
            fullTaskInfoFragment.setArguments(bundle)

            (context as AppCompatActivity).supportFragmentManager
                .beginTransaction()
                .replace(context.flFragment.id, fullTaskInfoFragment)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    init {
        inflater = LayoutInflater.from(context)
        this.taskList = taskList
        this.context = context
    }
}