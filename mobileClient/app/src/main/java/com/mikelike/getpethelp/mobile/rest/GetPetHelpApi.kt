package com.mikelike.getpethelp.mobile.rest

import com.android.volley.Response
import com.mikelike.getpethelp.mobile.viewModel.CreateCVViewModel
import com.mikelike.getpethelp.mobile.viewModel.CreateTaskViewModel
import com.mikelike.getpethelp.mobile.viewModel.LoginViewModel
import com.mikelike.getpethelp.mobile.viewModel.RegitsrationViewModel

interface GetPetHelpApi {
    fun getAllTasks()
    fun getAllWorkers()
    fun registration(registrationInput: RegitsrationViewModel.RegistrationInput)
    fun login(loginInput: LoginViewModel.LoginInput)
    fun getCurrentUserInfo()
    fun getCurrentUserWorker()
    fun createTask(taskInput: CreateTaskViewModel.TaskInput)
    fun createCV(workerInfoInput: CreateCVViewModel.WorkerInfoInput)
    fun getCurrentUserTasks()
    fun addResponseTOTask(id:Long)
}