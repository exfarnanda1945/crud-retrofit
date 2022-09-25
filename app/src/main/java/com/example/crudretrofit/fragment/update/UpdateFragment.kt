package com.example.crudretrofit.fragment.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crudretrofit.databinding.FragmentUpdateBinding
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.repository.PostRepository
import com.example.crudretrofit.utils.HandlerApiClient
import com.example.crudretrofit.viewmodel.MainViewModel
import com.example.crudretrofit.viewmodel.MainViewModelFactory

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()
    private val mViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            PostRepository()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        val title = binding.edtUpdateTitle
        val desc = binding.edtUpdateDescription

        title.setText(args.detailNote.title)
        desc.setText(args.detailNote.description)

        binding.btnUpdate.setOnClickListener {
            update(title, desc)
        }

        return view
    }

    private fun update(title: EditText, desc: EditText) {
        val post = PostModel(
            args.detailNote._id,
            args.detailNote.isImportant,
            args.detailNote.deadLine,
            title.text.toString(),
            desc.text.toString(),
            args.detailNote.createdAt,
            ""
        )
        mViewModel.update(post).observe(viewLifecycleOwner) {
           HandlerApiClient.handle(it,requireContext(),object :HandlerApiClient.HandlerCallback{
               override fun onSuccess() {
                   Toast.makeText(requireContext(), "Successfully update!", Toast.LENGTH_SHORT)
                       .show()
                   findNavController().navigateUp()
               }
           })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}