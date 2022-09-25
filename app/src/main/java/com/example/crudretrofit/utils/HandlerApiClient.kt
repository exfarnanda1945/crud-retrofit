package com.example.crudretrofit.utils

import android.content.Context
import android.widget.Toast
import com.example.crudretrofit.api.ApiResponse

object HandlerApiClient {
    fun <T> handle(response: ApiResponse<T>, context: Context, callback: HandlerCallback) {
        if (response.isSuccessful) {
            val responseData = response.data

            if (responseData?.code() == 200) {
                callback.onSuccess()
            } else {
                Toast.makeText(
                    context,
                    "Code ${responseData?.code()} ${responseData?.message()}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            Toast.makeText(
                context,
                response.exception?.message,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    interface HandlerCallback {
        fun onSuccess()
    }
}