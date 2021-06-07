package com.example.emitter.network

import com.example.emitter.pojo.UsersDataModelItem
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsersData(): List<UsersDataModelItem>
}