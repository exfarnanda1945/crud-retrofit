package com.example.crudretrofit.api

import retrofit2.Response
 object ApiHandler{
     inline fun <T> safeCallApi(apiCall: () -> Response<T>): ApiResponse<T> {
         return try {
             ApiResponse.success(apiCall.invoke())
         } catch (e: Exception) {
             ApiResponse.failure(e)
         }
     }
 }
