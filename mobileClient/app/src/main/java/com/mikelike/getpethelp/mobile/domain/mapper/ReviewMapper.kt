package com.mikelike.getpethelp.mobile.domain.mapper

import android.util.Log
import com.mikelike.getpethelp.mobile.domain.Review
import com.mikelike.getpethelp.mobile.domain.WorkerInfo
import org.json.JSONException
import org.json.JSONObject

class ReviewMapper {
    fun reviewFromTaskJsonArray(jsonObject: JSONObject): Review? {
        var review: Review? = null
        try {
            Log.d("fdsf",jsonObject.getJSONObject("workerInfo").toString())
            review = Review(
                jsonObject.getLong("id"),
                ShortUserInfoMapper().shortUserInfoFromTaskJsonArray(jsonObject),
                jsonObject.getString("message"),
                jsonObject.getDouble("rating"),
                jsonObject.getString("date")
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return review
    }
}