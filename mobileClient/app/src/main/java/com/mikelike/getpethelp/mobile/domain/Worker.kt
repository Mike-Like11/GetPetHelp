package com.mikelike.getpethelp.mobile.domain

import com.mikelike.getpethelp.backend.domain.ShortUserInfo
import java.io.Serializable

data class Worker(
    var id: Long? = null,
    val workerInfo: WorkerInfo? = null,
    val rating: Double = 0.0,
    val shortUserInfo: ShortUserInfo? = null,
    val tasks: List<Task>? = null,
    val reviews: List<Review?> = ArrayList<Review>()
) : Serializable
