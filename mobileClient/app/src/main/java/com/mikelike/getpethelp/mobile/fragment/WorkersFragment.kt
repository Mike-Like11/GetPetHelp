package com.mikelike.getpethelp.mobile.fragment

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.mikelike.getpethelp.mobile.R
import com.mikelike.getpethelp.mobile.adapter.TaskAdapter
import com.mikelike.getpethelp.mobile.adapter.WorkerAdapter
import com.mikelike.getpethelp.mobile.databinding.TasksFragmentBinding
import com.mikelike.getpethelp.mobile.databinding.WorkersFragmentBinding
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB
import com.mikelike.getpethelp.mobile.viewModel.TasksViewModel
import com.mikelike.getpethelp.mobile.viewModel.WorkersViewModel

class WorkersFragment : Fragment() {

    private lateinit var viewModel: WorkersViewModel
    private var _binding: WorkersFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        viewModel = ViewModelProvider(this).get(WorkersViewModel::class.java)
        viewModel.getWorkers()
        _binding = WorkersFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Исполнители"
        val text: Spannable = SpannableString((requireActivity() as AppCompatActivity).supportActionBar?.title)
        text.setSpan(
            ForegroundColorSpan(Color.rgb(254,208,83)),
            0,
            text.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        viewModel.loading.observeForever {
            binding.progressDialog.isVisible = it
        }
        binding.allWorkers.adapter = WorkerAdapter(requireActivity(), GetPetHelpFakeDB.WORKER_LIST)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}