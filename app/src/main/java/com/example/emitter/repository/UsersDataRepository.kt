package com.example.emitter.repository

import com.example.emitter.network.RetrofitClient
import com.example.emitter.pojo.UsersDataModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UsersDataRepository {

    companion object{
        fun getUser(): Flow<List<UsersDataModelItem>> = flow {
            val response = RetrofitClient.api.getUsersData()
            emit(response)
        }.flowOn(Dispatchers.IO)

    }
}