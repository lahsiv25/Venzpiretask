package com.vishal.venzpiretask.api
import com.vishal.venzpiretask.model.Get
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GetApi {

    @GET("{number}/{type}")
    suspend fun getResult(
        @Path("number") number:String,
        @Path("type") type:String,
        @QueryMap queries: Map<String,String>
    ):Response<Get>
}