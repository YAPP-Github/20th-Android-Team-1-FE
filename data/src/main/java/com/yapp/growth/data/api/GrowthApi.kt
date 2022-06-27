package com.yapp.growth.data.api

import com.yapp.growth.data.response.UserResponse
import retrofit2.http.GET

interface GrowthApi {

    @GET("/users")
    suspend fun getUsers(): List<UserResponse>

}