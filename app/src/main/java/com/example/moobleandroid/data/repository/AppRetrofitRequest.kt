package com.example.moobleandroid.data.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AppRetrofitRequest {

    fun <T> getResponse(
        onSuccess: (response: T) -> Unit, onFail: (responseCode: Int, message: String) -> Unit,
        onFailure: ((call: Call<T>, throwable: Throwable) -> Unit)? = null
    )
            :Callback<T>{
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when {
                    response.isSuccessful -> response.body()?.let { onSuccess(it) }
                    else -> {
                        Log.d("LOG" , "header: ${response.headers()}")
                        Log.e("LOG", "code: ${response.code()}")
                        Log.e("LOG", "message: ${response.message()}")
                        onFail(response.code(), response.message())
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure?.let { it(call, t) }
                Log.e("LOG", "onFailure: ${t.message.toString()}")
            }

        }
    }

}