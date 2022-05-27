package com.mikelike.getpethelp.mobile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.mikelike.getpethelp.mobile.databinding.ShortTaskItemBinding
import com.mikelike.getpethelp.mobile.databinding.TaskInfoProfileBinding
import com.mikelike.getpethelp.mobile.domain.Task
import com.mikelike.getpethelp.mobile.fragment.FullTaskInfoFragment
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.short_task_item.view.*
import kotlinx.android.synthetic.main.task_info_profile.view.*

class TaskInfoProfileAdapter(context: Context, taskList: List<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val inflater: LayoutInflater
        private val taskList: List<Task>
        private val context: Context

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = TaskInfoProfileBinding.inflate(inflater, parent, false)
            return MyViewHolder(binding.root)
        }

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            @SuppressLint("RecyclerView") position: Int
        ) {
            val task = taskList[position]
            Log.d("qweq",task.toString())
            holder.itemView.my_task_name.text = task.taskInfo?.name.toString()
            holder.itemView.my_task_date.text = task.taskInfo?.dateOfPerformance.toString()
            holder.itemView.all_task_workers.adapter = WorkerTaskAdapter(context, task.workerInfoList)
            holder.itemView.my_task_location.text = getAddress(LatLng(task.taskInfo?.latitude!!,
                task.taskInfo.longitude!!
            ),context)
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