package com.vishal.venzpiretask.api

import com.vishal.venzpiretask.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api:GetApi by lazy {
        retrofit.create(GetApi::class.java)
    }
}