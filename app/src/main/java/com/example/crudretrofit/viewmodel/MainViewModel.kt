package com.example.crudretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.repository.PostRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repo:PostRepository):ViewModel() {
    val response:MutableLiveData<Response<List<PostModel>>> = MutableLiveData()


    fun list(){
        viewModelScope.launch {
            val res = repo.list()
            response.value = res
        }
    }

    fun post(post:PostModel){
        viewModelScope.launch {
           repo.post(post)
        }
    }

    fun delete(id:IdModel){
        viewModelScope.launch {
            repo.delete(id)
        }
    }

    fun update(post:PostModel){
        viewModelScope.launch{
            repo.update(post)
        }
    }

}