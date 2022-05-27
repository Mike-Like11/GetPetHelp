package com.mikelike.getpethelp.mobile.domain

data class FullUserInfo(
    val id: Long,
    val user: User,
    val middleName: String,
    val age: Int,
    val city: String,
    val telegram: Boolean,
    val whatsApp: Boolean,
    val viber: Boolean
)