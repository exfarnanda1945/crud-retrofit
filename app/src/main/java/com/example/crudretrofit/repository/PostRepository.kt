package com.example.crudretrofit.repository

import com.example.crudretrofit.api.ApiHandler
import com.example.crudretrofit.api.ApiResponse
import com.example.crudretrofit.api.RetrofitInstance
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.models.PostModelDelete

class PostRepository {
    suspend fun list(): ApiResponse<List<PostModel>> = ApiHandler.safeCallApi { RetrofitInstance.api.list() }

    suspend fun post(post: PostModel): ApiResponse<PostModel> = ApiHandler.safeCallApi { RetrofitInstance.api.post(post) }

    suspend fun delete(id: IdModel): ApiResponse<PostModelDelete> = ApiHandler.safeCallApi { RetrofitInstance.api.delete(id) }

    suspend fun update(post: PostModel): ApiResponse<Unit> = ApiHandler.safeCallApi { RetrofitInstance.api.update(post) }
}