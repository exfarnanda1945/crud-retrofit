package com.example.crudretrofit.api

import retrofit2.Response


data class ApiResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {

    companion object{
        fun <T> success(data:Response<T>):ApiResponse<T>{
            return ApiResponse(Status.Success,data,null)
        }

        fun <T> failure(exception: Exception):ApiResponse<T>{
            return ApiResponse(Status.Failure,null,exception)
        }
    }

    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

     val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful:Boolean
        get() = !failed && this.data?.isSuccessful == true

    val body:T
        get() = this.data!!.body()!!
}