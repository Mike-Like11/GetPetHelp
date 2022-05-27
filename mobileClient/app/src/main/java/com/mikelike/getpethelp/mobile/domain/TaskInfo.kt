package com.mikelike.getpethelp.mobile.domain;

data class TaskInfo(
    var id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val dateOfPerformance: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null
)