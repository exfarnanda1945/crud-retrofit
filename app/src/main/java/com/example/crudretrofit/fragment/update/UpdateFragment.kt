package com.example.crudretrofit.fragment.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crudretrofit.R
import com.example.crudretrofit.databinding.FragmentUpdateBinding
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.repository.PostRepository
import com.example.crudretrofit.viewmodel.MainViewModel
import com.example.crudretrofit.viewmodel.MainViewModelFactory

class UpdateFragment : Fragment() {
    private var _binding:FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var mViewModel:MainViewModel
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        val view = binding.root

        val title = binding.edtUpdateTitle
        val desc = binding.edtUpdateDescription

        title.setText(args.detailNote.title)
        desc.setText(args.detailNote.description)

        val repo = PostRepository()
        val mViewModelFactory = MainViewModelFactory(repo)
        mViewModel = ViewModelProvider(this,mViewModelFactory)[MainViewModel::class.java]

        binding.btnUpdate.setOnClickListener {
            update(title,desc)
        }

        return view
    }

    private fun update(title:EditText,desc:EditText) {
        val post = PostModel(args.detailNote._id,args.detailNote.isImportant,args.detailNote.deadLine,title.text.toString(),desc.text.toString(),args.detailNote.createdAt,"")
        mViewModel.update(post)
        Toast.makeText(requireContext(),"Successfully update!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        mViewModel.list()
    }
}