package com.example.crudretrofit.fragment.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.crudretrofit.databinding.FragmentAddBinding
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.repository.PostRepository
import com.example.crudretrofit.utils.HandlerApiClient
import com.example.crudretrofit.viewmodel.MainViewModel
import com.example.crudretrofit.viewmodel.MainViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddFragment : Fragment() {
    private var _binding:FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val mViewModel: MainViewModel by activityViewModels { MainViewModelFactory(PostRepository()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        binding.btnSave.setOnClickListener {
            post()
        }

        return view
    }

    private fun post() {
        val title = binding.edtTitle.text
        val body = binding.edtDescription.text

        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(body)){
            Toast.makeText(requireContext(),"Please fill the fields",Toast.LENGTH_SHORT).show()
        }else{
            val date: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            val post = PostModel("",true,date,title.toString(),body.toString(),"","" )
            mViewModel.post(post).observe(viewLifecycleOwner){
                HandlerApiClient.handle(it,requireContext(),object:HandlerApiClient.HandlerCallback{
                    override fun onSuccess() {
                        Toast.makeText(requireContext(),"Successfully added!",Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                })
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}