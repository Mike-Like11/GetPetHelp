package com.mikelike.getpethelp.mobile.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.mikelike.getpethelp.mobile.adapter.TaskAdapter
import com.mikelike.getpethelp.mobile.databinding.TasksFragmentBinding
import com.mikelike.getpethelp.mobile.viewModel.TasksViewModel
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB.Companion
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB.Companion.TASK_LIST

class TasksFragment : Fragment() {


    private lateinit var viewModel: TasksViewModel
    private var _binding: TasksFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        viewModel.getTasks()
        _binding = TasksFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        viewModel.loading.observeForever {
            binding.progressDialog.isVisible = it
        }
        binding.allTasks.adapter = TaskAdapter(requireActivity(), TASK_LIST)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}