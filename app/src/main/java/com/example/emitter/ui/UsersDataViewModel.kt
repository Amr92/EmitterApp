package com.example.emitter.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emitter.network.RetrofitClient
import com.example.emitter.pojo.UsersDataModelItem
import com.example.emitter.repository.UsersDataRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersDataViewModel: ViewModel() {

    val users: MutableLiveData<List<UsersDataModelItem>> by lazy {
        MutableLiveData<List<UsersDataModelItem>>().also {
            getData()
        }
    }

    private fun getData() {

        viewModelScope.launch {
            UsersDataRepository.getUser()
                .catch {e ->
                    Log.d("TAG", "getData: ${e.message}")
                }.collect{response ->
                    users.value = response

                }
        }
    }


}