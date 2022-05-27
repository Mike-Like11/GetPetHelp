package com.mikelike.getpethelp.backend.domain;

import com.mikelike.getpethelp.mobile.domain.User


data class ShortUserInfo(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var avatarUrl: String,
    var phone: String,
)
