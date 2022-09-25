package com.example.crudretrofit.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel(
    val _id:String?,
    val isImportant:Boolean,
    val deadLine: String,
    val title:String,
    val description:String,
    val createdAt:String?,
    val updateAt:String?
):Parcelable

data class PostModelDelete(
    val acknowledged:Boolean,
    val deleteCount:Int
)

data class IdModel(
    val id:String
)