package com.example.crudretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.crudretrofit.databinding.ActivityMainBinding
import com.example.crudretrofit.repository.PostRepository
import com.example.crudretrofit.viewmodel.MainViewModel
import com.example.crudretrofit.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(findNavController(R.id.fragmentContainerView))
    }
}