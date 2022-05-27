package com.mikelike.getpethelp.mobile.domain.mapper

import com.mikelike.getpethelp.backend.domain.ShortUserInfo
import org.json.JSONException

import org.json.JSONObject


class ShortUserInfoMapper {
    fun shortUserInfoFromTaskJsonArray(jsonObject: JSONObject): ShortUserInfo? {
        var shortUserInfo: ShortUserInfo? = null
        try {
            shortUserInfo = ShortUserInfo(
                jsonObject.getJSONObject("shortUserInfo").getLong("id"),
                jsonObject.getJSONObject("shortUserInfo").getString("firstName"),
                jsonObject.getJSONObject("shortUserInfo").getString("lastName"),
                jsonObject.getJSONObject("shortUserInfo").getString("avatarUrl"),
                jsonObject.getJSONObject("shortUserInfo").getString("phone")
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return shortUserInfo
    }
}