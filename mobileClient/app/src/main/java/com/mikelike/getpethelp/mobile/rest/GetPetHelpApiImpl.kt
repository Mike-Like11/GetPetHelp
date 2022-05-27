package com.mikelike.getpethelp.mobile.rest

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.*
import com.mikelike.getpethelp.backend.domain.ShortUserInfo
import com.mikelike.getpethelp.mobile.domain.*
import com.mikelike.getpethelp.mobile.domain.mapper.ReviewMapper
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB
import com.mikelike.getpethelp.mobile.domain.mapper.ShortUserInfoMapper
import com.mikelike.getpethelp.mobile.domain.mapper.TaskInfoMapper
import com.mikelike.getpethelp.mobile.domain.mapper.WorkerInfoMapper
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB.Companion.MY_TASK_LIST
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB.Companion.TASK_LIST
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB.Companion.WORKER_LIST
import com.mikelike.getpethelp.mobile.viewModel.CreateCVViewModel
import com.mikelike.getpethelp.mobile.viewModel.CreateTaskViewModel
import com.mikelike.getpethelp.mobile.viewModel.LoginViewModel
import com.mikelike.getpethelp.mobile.viewModel.RegitsrationViewModel
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import kotlin.math.min


class GetPetHelpApiImpl(context: Context?) : GetPetHelpApi {
    val BASE_URL = "http://192.168.1.4:8080"
    private lateinit var context: Context


    init {
        this.context = context!!
    }
    override fun getAllTasks() {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/tasks"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    TASK_LIST.clear()
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val shortUserInfo:ShortUserInfo? = ShortUserInfoMapper().shortUserInfoFromTaskJsonArray(jsonObject)
                        val taskInfo:TaskInfo? = TaskInfoMapper().taskInfoFromTaskJsonArray(jsonObject)
                        val task: Task = Task(
                            jsonObject.getLong("id"),
                            shortUserInfo,
                            taskInfo,
                            ArrayList(),
                            null,
                            jsonObject.getString("dateOfCreation")
                        )
                        TASK_LIST.add(task)
                    }
                } catch (e: JSONException) {
                }
            },
            Response.ErrorListener { error -> error.printStackTrace() },
        )

        queue.add(jsonArrayRequest)
    }

    override fun getAllWorkers() {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/workers"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    WORKER_LIST.clear()
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val shortUserInfo:ShortUserInfo? = ShortUserInfoMapper().shortUserInfoFromTaskJsonArray(jsonObject)
                        val workerInfo:WorkerInfo? = WorkerInfoMapper().workerInfoFromTaskJsonArray(jsonObject)
                        val reviews = ArrayList<Review?>()
                        for (i in 0 until jsonObject.getJSONArray("reviews").length()) {
                            reviews.add(ReviewMapper().reviewFromTaskJsonArray(jsonObject.getJSONArray("reviews").getJSONObject(i)))
                        }
                        val worker: Worker = Worker(
                            jsonObject.getLong("id"),
                            workerInfo,
                            jsonObject.getDouble("rating"),
                            shortUserInfo,
                            reviews = reviews,
                        )
                        WORKER_LIST.add(worker)
                    }
                } catch (e: JSONException) {
                }
            },
            Response.ErrorListener { error -> error.printStackTrace() },
        )

        queue.add(jsonArrayRequest)
    }

    override fun registration(registrationInput: RegitsrationViewModel.RegistrationInput) {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/auth/register"
        var request = object : VolleyFileUploadRequest(
            Method.POST,
            url,
            Response.Listener {
                println("response is: $it")
            },
            Response.ErrorListener {
                println("error is: $it")
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["firstName"] = registrationInput.firstName.toString()
                params["middleName"] = registrationInput.middleName.toString()
                params["lastName"] = registrationInput.lastName.toString()
                params["city"] = registrationInput.city.toString()
                params["phone"] = registrationInput.phone.toString()
                params["telegram"] = true.toString()
                params["whatsApp"] = true.toString()
                params["viber"] = true.toString()
                params["age"] = registrationInput.age.toString()
                params["email"] = registrationInput.email.toString()
                params["password"] = registrationInput.password.toString()
                return params
            }
            override fun getByteData(): HashMap<String, FileDataPart> {
                val params = HashMap<String, FileDataPart>()
                if(registrationInput.avatar!=null) {
                    val baos = ByteArrayOutputStream()
                    registrationInput.avatar.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val imageBytes: ByteArray = baos.toByteArray()
                    val encodedImage: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
                    params["avatar"] = FileDataPart("image",imageBytes, "jpeg")
                }
                val jsonObject = JSONObject(params as Map<*, *>?)
                return params
            }
        }
        queue.add(request)
    }

    override fun login(loginInput: LoginViewModel.LoginInput) {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/auth/login"
        val params = HashMap<String, String>()
        val json = JSONObject()
        json.put("email",loginInput.email.toString())
        json.put("password", loginInput.password.toString())
        val request = object : StringRequest(Request.Method.POST, url,
            { stringResponse ->
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.putString("key", stringResponse.toString())
                editor.commit() // or editor.apply();
            },
            { volleyError ->  }) {
            override fun getBody(): ByteArray {
                return json.toString().toByteArray();
            }

            override fun getBodyContentType(): String {
                return "application/json";
            }
        }
        queue.add(request)
    }
    override fun createTask(taskInput: CreateTaskViewModel.TaskInput) {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/user/tasks"
        val json = JSONObject()
        json.put("name",taskInput.name.toString())
        json.put("description", taskInput.description.toString())
        json.put("dateOfPerformance", taskInput.dateOfPerformance.toString())
        json.put("latitude", taskInput.latitude.toString())
        json.put("longitude", taskInput.longitude.toString())
        val request = object : JsonObjectRequest(Request.Method.POST, url,null,
            { stringResponse ->

            },
            { volleyError ->  }) {
            override fun getBody(): ByteArray {
                return json.toString().toByteArray();
            }
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val accesstoken = sharedpreferences.getString("key","")
                headers["Authorization"] = "Bearer $accesstoken"
                return headers
            }
            override fun getBodyContentType(): String {
                return "application/json";
            }
        }
        queue.add(request)
    }

    override fun createCV(workerInfoInput: CreateCVViewModel.WorkerInfoInput) {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/user/worker"
        val json = JSONObject()
        json.put("preferences",workerInfoInput.preferences.toString())
        json.put("experience", workerInfoInput.experience.toString())
        val request = object : JsonObjectRequest(Request.Method.POST, url,null,
            { stringResponse ->
            },
            { volleyError ->  }) {
            override fun getBody(): ByteArray {
                return json.toString().toByteArray();
            }
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val accesstoken = sharedpreferences.getString("key","")
                headers["Authorization"] = "Bearer $accesstoken"
                return headers
            }
            override fun getBodyContentType(): String {
                return "application/json";
            }
        }
        queue.add(request)
    }

    override fun getCurrentUserInfo(){
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/auth/user/info"
        val request = object : JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.putString("user", response.toString());
                editor.commit()
            },
            { volleyError ->  }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val accesstoken = sharedpreferences.getString("key","")
                headers["Authorization"] = "Bearer $accesstoken"
                return headers
            }
        }
        request.retryPolicy = DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }
    override fun getCurrentUserWorker(){
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/user/worker"
        val request = object : JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.putString("worker", response.toString());
                editor.commit()
            },
            { volleyError ->  }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val accesstoken = sharedpreferences.getString("key","")
                headers["Authorization"] = "Bearer $accesstoken"
                return headers
            }
        }
        request.retryPolicy = DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }

    override fun getCurrentUserTasks() {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/user/tasks"
        val request = object : JsonArrayRequest(Request.Method.GET, url,null,
            { response ->
                MY_TASK_LIST.clear()
                Log.d("fdsf",response.toString())
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val shortUserInfo:ShortUserInfo? = ShortUserInfoMapper().shortUserInfoFromTaskJsonArray(jsonObject)
                    val taskInfo:TaskInfo? = TaskInfoMapper().taskInfoFromTaskJsonArray(jsonObject)
                    val workerInfList = ArrayList<Worker>()
                    for (j in 0 until jsonObject.getJSONArray("workerInfoList").length()) {
                        val jsonObject2 = jsonObject.getJSONArray("workerInfoList").getJSONObject(j)
                        //Log.d("fdsf",jsonObject2.getJSONObject("workerInfo").toString())
                        Log.d("fdsf10",jsonObject2.toString())
                        val shortUserInfo2:ShortUserInfo? = ShortUserInfoMapper().shortUserInfoFromTaskJsonArray(jsonObject2)
                        val workerInfo:WorkerInfo? = WorkerInfoMapper().workerInfoFromTaskJsonArray(jsonObject2)
                        val worker: Worker = Worker(
                            jsonObject2.getLong("id"),
                            workerInfo,
                            jsonObject2.getDouble("rating"),
                            shortUserInfo,
                            reviews = ArrayList(),
                        )
                        workerInfList.add(worker)
                    }
                    val task: Task = Task(
                        jsonObject.getLong("id"),
                        shortUserInfo,
                        taskInfo,
                        workerInfList,
                        null,
                        jsonObject.getString("dateOfCreation")
                    )
                    MY_TASK_LIST.add(task)
                }
            },
            { volleyError ->  }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val accesstoken = sharedpreferences.getString("key","")
                headers["Authorization"] = "Bearer $accesstoken"
                return headers
            }
        }
        request.retryPolicy = DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }

    override fun addResponseTOTask(id: Long) {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/api/tasks/$id/respond"
        val request = object : JsonArrayRequest(Request.Method.GET, url,null,
            { response ->
            },
            { volleyError ->  }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val sharedpreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val accesstoken = sharedpreferences.getString("key","")
                headers["Authorization"] = "Bearer $accesstoken"
                return headers
            }
        }
        request.retryPolicy = DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }
}