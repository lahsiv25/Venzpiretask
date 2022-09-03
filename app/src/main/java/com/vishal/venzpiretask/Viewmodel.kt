package com.vishal.venzpiretask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishal.venzpiretask.model.Get
import com.vishal.venzpiretask.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class Viewmodel(private val repository: Repository):ViewModel() {
    val myresponse:MutableLiveData<Response<Get>> = MutableLiveData()
    fun getResult(number:String,type:String,queries:Map<String,String>){
        viewModelScope.launch {
            val response = repository.getvalue(number,type,queries)
            myresponse.value = response
        }
    }
}