package com.example.crudretrofit.fragment.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.crudretrofit.R
import com.example.crudretrofit.databinding.FragmentAddBinding
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.repository.PostRepository
import com.example.crudretrofit.viewmodel.MainViewModel
import com.example.crudretrofit.viewmodel.MainViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddFragment : Fragment() {
    private var _binding:FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mViewModel:MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        val repo = PostRepository()
        val mViewModelFactory = MainViewModelFactory(repo)
        mViewModel = ViewModelProvider(this,mViewModelFactory).get(MainViewModel::class.java)

        binding.btnSave.setOnClickListener {
            post()
        }

        return view
    }

    private fun post() {
        val title = binding.edtTitle.text
        val body = binding.edtDescription.text

        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(body)){
            Toast.makeText(requireContext(),"Please fill the fields",Toast.LENGTH_LONG).show()
        }else{
            val date: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            val post = PostModel("",true,date,title.toString(),body.toString(),"","" )
            mViewModel.post(post)
            Toast.makeText(requireContext(),"Successfully added!",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
            mViewModel.list()
        }



    }

}