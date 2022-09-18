package com.example.crudretrofit.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.crudretrofit.R

class LoadingBar(private val inflater:LayoutInflater, private val ctx:Context) {
    private lateinit var isDialog:AlertDialog

    fun start(){
        val dialogView = inflater.inflate(R.layout.loading,null)
        val builder = AlertDialog.Builder(ctx)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun stop(){
        isDialog.dismiss()
    }
}