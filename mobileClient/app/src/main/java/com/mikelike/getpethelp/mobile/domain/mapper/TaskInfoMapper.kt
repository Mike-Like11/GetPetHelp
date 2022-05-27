package com.mikelike.getpethelp.mobile.domain.mapper

import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.mikelike.getpethelp.backend.domain.ShortUserInfo
import com.mikelike.getpethelp.mobile.MainActivity
import com.mikelike.getpethelp.mobile.domain.TaskInfo
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class TaskInfoMapper {
    fun taskInfoFromTaskJsonArray(jsonObject: JSONObject): TaskInfo? {
        var taskInfo: TaskInfo? = null
        try {
            Log.d("fdsf",jsonObject.getJSONObject("taskInfo").toString())
            taskInfo = TaskInfo(
                jsonObject.getJSONObject("taskInfo").getLong("id"),
                jsonObject.getJSONObject("taskInfo").getString("name"),
                jsonObject.getJSONObject("taskInfo").getString("description"),
                jsonObject.getJSONObject("taskInfo").getString("dateOfPerformance"),
                jsonObject.getJSONObject("taskInfo").getDouble("latitude"),
                jsonObject.getJSONObject("taskInfo").getDouble("longitude")
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return taskInfo
    }
}