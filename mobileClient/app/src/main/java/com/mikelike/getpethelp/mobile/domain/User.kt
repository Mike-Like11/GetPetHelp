package com.mikelike.getpethelp.mobile.domain

import com.mikelike.getpethelp.backend.domain.ShortUserInfo

data class User(
    var id: Long,
    var email: String,
    var password: String,
    var role: String,
    var fullUserInfo: FullUserInfo,
    var shortUserInfo: ShortUserInfo
)