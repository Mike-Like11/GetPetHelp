package com.mikelike.getpethelp.mobile.domain

import com.mikelike.getpethelp.backend.domain.ShortUserInfo
import java.io.Serializable


data class Task (
    var id: Long,
    val user: ShortUserInfo?,
    val taskInfo: TaskInfo?,
    val workerInfoList: List<Worker>,
    val worker: Worker?,
    val dateOfCreation: String,
) : Serializable