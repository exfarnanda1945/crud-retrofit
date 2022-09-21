package com.example.crudretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.repository.PostRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repo:PostRepository):ViewModel() {
   private var _response:MutableLiveData<Response<List<PostModel>>> = MutableLiveData()
    val response: LiveData<Response<List<PostModel>>> get() = _response

    fun list(){
        viewModelScope.launch {
            val res = repo.list()
            _response.value = res
        }
    }

    fun post(post:PostModel){
        viewModelScope.launch {
           repo.post(post)
            list()
        }
    }

    fun delete(id:IdModel){
        viewModelScope.launch {
            repo.delete(id)
            list()
        }
    }

    fun update(post:PostModel){
        viewModelScope.launch{
            repo.update(post)
            list()
        }
    }

}