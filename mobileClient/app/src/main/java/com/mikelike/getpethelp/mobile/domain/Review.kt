package com.mikelike.getpethelp.mobile.domain

import com.mikelike.getpethelp.backend.domain.ShortUserInfo

data class Review(
    var id: Long? = null,
    val user: ShortUserInfo? = null,
    val message: String? = null,
    val rating: Double? = null,
    val date: String? = null
 )