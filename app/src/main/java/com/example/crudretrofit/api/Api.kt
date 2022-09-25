package com.example.crudretrofit.api

import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.models.PostModelDelete
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("/note")
    suspend fun list(): Response<List<PostModel>>

    @POST("/note")
    suspend fun post(
        @Body post: PostModel
    ): Response<PostModel>

    @POST("/note/delete")
    suspend fun delete(@Body id:IdModel):Response<PostModelDelete>

    @PUT("/note")
    suspend fun update(@Body post:PostModel):Response<Unit>
}