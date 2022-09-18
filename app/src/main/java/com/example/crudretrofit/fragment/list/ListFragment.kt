package com.example.crudretrofit.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudretrofit.R
import com.example.crudretrofit.databinding.FragmentListBinding
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.repository.PostRepository
import com.example.crudretrofit.utils.LoadingBar
import com.example.crudretrofit.viewmodel.MainViewModel
import com.example.crudretrofit.viewmodel.MainViewModelFactory

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mViewModel: MainViewModel
    private lateinit var loading:LoadingBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        loading = LoadingBar(layoutInflater,requireContext())
        loading.start()

        get(view,inflater)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }

    private fun get(view:View,inflater:LayoutInflater) {
        val rv = binding.rvPost
        val repo = PostRepository()
        val mViewModelFac = MainViewModelFactory(repo)
        mViewModel = ViewModelProvider(this, mViewModelFac).get(MainViewModel::class.java)
        mViewModel.list()
        mViewModel.response.observe(viewLifecycleOwner, Observer { res ->
            if (res.isSuccessful) {
                val response = res.body()
                    if(response?.isEmpty() == true){
                        binding.rvPost.visibility = View.INVISIBLE
                        binding.tvEmptyList.visibility =  View.VISIBLE
                    }else{
                        renderRv(response,rv,view)
                    }
                loading.stop()
            }
        })
    }


    private fun renderRv(resBody:List<PostModel>?,rv:RecyclerView,view:View) {
        val rvAdapter = ListRvAdapter(resBody!!)
        rv.apply {
            adapter = rvAdapter
            rv.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)

        }

        rvAdapter.setOnItemClickCallback(object:ListRvAdapter.IOnItemCallBack{
            override fun delete(id: IdModel,position:Int) {
                val alertBuilder = AlertDialog.Builder(requireContext())
                alertBuilder.setPositiveButton("Yes"){_,_, ->
                    mViewModel.delete(id)
                    Toast.makeText(requireContext(),"Delete succesfully",Toast.LENGTH_LONG).show()
                    rvAdapter.notifyItemRemoved(position)
                    mViewModel.list()
                }

                alertBuilder.setNegativeButton("No"){_,_ ->}
                alertBuilder.setTitle("Delete this?")
                alertBuilder.setMessage("Are you sure want to delete this")
                alertBuilder.create().show()
            }
        })
    }


}