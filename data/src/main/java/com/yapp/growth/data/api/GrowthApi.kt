package com.yapp.growth.data.api

import com.yapp.growth.data.response.UserResponse
import retrofit2.http.GET

const val BASE_URL = "https://api.github.com"
interface GrowthApi {

    @GET("/users")
    suspend fun getUsers(): List<UserResponse>

}