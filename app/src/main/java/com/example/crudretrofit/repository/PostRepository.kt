package com.example.crudretrofit.repository

import com.example.crudretrofit.api.RetrofitInstance
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.models.PostModelDelete
import retrofit2.Response

class PostRepository {
    suspend fun list(): Response<List<PostModel>> = RetrofitInstance.api.list()

    suspend fun post(post:PostModel):Response<PostModel> = RetrofitInstance.api.post(post)

    suspend fun delete(id:IdModel):Response<PostModelDelete> = RetrofitInstance.api.delete(id)

    suspend fun update(post:PostModel) = RetrofitInstance.api.update(post)
}