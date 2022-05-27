package com.mikelike.getpethelp.mobile.domain.mapper

import android.util.Log
import com.mikelike.getpethelp.mobile.domain.TaskInfo
import com.mikelike.getpethelp.mobile.domain.WorkerInfo
import org.json.JSONException
import org.json.JSONObject

class WorkerInfoMapper {
    fun workerInfoFromTaskJsonArray(jsonObject: JSONObject): WorkerInfo? {
        var workerInfo: WorkerInfo? = null
        try {
            Log.d("fdsf",jsonObject.getJSONObject("workerInfo").toString())
            workerInfo = WorkerInfo(
                jsonObject.getJSONObject("workerInfo").getLong("id"),
                jsonObject.getJSONObject("workerInfo").getString("experience"),
                jsonObject.getJSONObject("workerInfo").getString("preferences"),
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return workerInfo
    }
}