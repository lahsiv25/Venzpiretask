package com.vishal.venzpiretask.repository

import com.vishal.venzpiretask.api.RetrofitInstance
import com.vishal.venzpiretask.model.Get
import retrofit2.Response

class Repository {
    suspend fun getvalue(number:String,type:String,queries:Map<String,String>):Response<Get>{
       return RetrofitInstance.api.getResult(number,type,queries)
    }
}