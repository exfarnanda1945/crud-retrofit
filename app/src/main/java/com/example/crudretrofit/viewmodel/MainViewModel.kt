package com.example.crudretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudretrofit.api.ApiResponse
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.models.PostModelDelete
import com.example.crudretrofit.repository.PostRepository
import kotlinx.coroutines.launch
import retrofit2.Response

//shared viewmodel
class MainViewModel(private val repo: PostRepository) : ViewModel() {
    fun list():LiveData<ApiResponse<List<PostModel>>> {
        val result = MutableLiveData<ApiResponse<List<PostModel>>>()
        viewModelScope.launch {
            val res = repo.list()
            result.postValue(res)
        }

        return result
    }

    // https://stackoverflow.com/questions/60910978/how-to-return-value-from-async-coroutine-scope-such-as-viewmodelscope-to-your-ui
    fun post(post: PostModel):LiveData<ApiResponse<PostModel>> {
        val result = MutableLiveData<ApiResponse<PostModel>>()
        viewModelScope.launch {
           val response= repo.post(post)
            result.postValue(response)
        }

        return result
    }

    fun delete(id: IdModel):LiveData<ApiResponse<PostModelDelete>> {
        val result = MutableLiveData<ApiResponse<PostModelDelete>>()
        viewModelScope.launch {
            val respon =             repo.delete(id)
            result.postValue(respon)
        }

        return result
    }

    fun update(post: PostModel):LiveData<ApiResponse<Unit>> {
        val result = MutableLiveData<ApiResponse<Unit>>()
        viewModelScope.launch {
            val respon = repo.update(post)
            result.postValue(respon)
        }

        return result
    }

}