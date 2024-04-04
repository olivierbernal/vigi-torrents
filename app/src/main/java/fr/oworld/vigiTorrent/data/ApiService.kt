package fr.oworld.vigiTorrent.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @GET("/api/signalements")
    fun getSignalements(): Call<String>

    @Headers("Content-Type: application/json")
    @POST("/api/signalement")
    fun postSignalement(@Body body: String): Call<String>

    @Headers("Content-Type: application/json")
    @POST("/api/subscription\n")
    fun postSubscription(@Body body: String): Call<String>
}