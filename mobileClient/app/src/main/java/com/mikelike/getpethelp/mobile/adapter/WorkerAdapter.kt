package com.mikelike.getpethelp.mobile.adapter

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mikelike.getpethelp.mobile.MainActivity
import com.mikelike.getpethelp.mobile.databinding.ShortWorkerItemBinding
import com.mikelike.getpethelp.mobile.domain.FullUserInfo
import com.mikelike.getpethelp.mobile.domain.Worker
import com.mikelike.getpethelp.mobile.fragment.FullTaskInfoFragment
import com.mikelike.getpethelp.mobile.fragment.FullWorkerInfoFragment
import com.mikelike.getpethelp.mobile.fragment.MyProfileFragment
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.short_task_item.view.*
import kotlinx.android.synthetic.main.short_worker_item.view.*


class WorkerAdapter(context: Context, workerList: List<Worker>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater
    private val workerList: List<Worker>
    private val context: Context

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ShortWorkerItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val worker = workerList[position]
        Log.d("qweq",worker.toString())
        holder.itemView.worker_name.text = worker.shortUserInfo?.firstName.toString().plus(" ").plus(worker.shortUserInfo?.lastName.toString())
        holder.itemView.worker_rating.rating = worker.rating.toFloat()
        val imageLoader: ImageLoader =  ImageLoader.getInstance()
        imageLoader.displayImage(worker.shortUserInfo?.avatarUrl, holder.itemView.worker_avatar)
        holder.itemView.setOnClickListener{
            val fullWorkerInfoFragment = FullWorkerInfoFragment()
            val bundle = Bundle()
            bundle.putSerializable("worker", worker)
            fullWorkerInfoFragment.setArguments(bundle)

            (context as AppCompatActivity).supportFragmentManager
                .beginTransaction()
                .replace(context.flFragment.id, fullWorkerInfoFragment)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return workerList.size
    }

    init {
        inflater = LayoutInflater.from(context)
        this.workerList = workerList
        this.context = context
    }
}