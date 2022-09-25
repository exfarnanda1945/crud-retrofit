package com.example.crudretrofit.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudretrofit.R
import com.example.crudretrofit.databinding.FragmentListBinding
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel
import com.example.crudretrofit.repository.PostRepository
import com.example.crudretrofit.utils.HandlerApiClient
import com.example.crudretrofit.utils.LoadingBar
import com.example.crudretrofit.viewmodel.MainViewModel
import com.example.crudretrofit.viewmodel.MainViewModelFactory

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val mViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            PostRepository()
        )
    }
    private lateinit var loading: LoadingBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        loading = LoadingBar(layoutInflater, requireContext())
        loading.start()

        get(view)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }

    private fun get(view: View) {
        val rv = binding.rvPost
        mViewModel.list().observe(viewLifecycleOwner) { response ->
            Log.d("LISTFRAGMENT","observer called")
            HandlerApiClient.handle(response,requireContext(),object :HandlerApiClient.HandlerCallback{
                override fun onSuccess() {
                    val responseBody = response.body
                    if (responseBody.isEmpty()) {
                        binding.rvPost.visibility = View.INVISIBLE
                        binding.tvEmptyList.visibility = View.VISIBLE
                    }
                    renderRv(responseBody, rv, view)
                }

            })
            loading.stop()
        }
    }


    private fun renderRv(resBody: List<PostModel>?, rv: RecyclerView, view: View) {
        val rvAdapter = ListRvAdapter()
        rv.apply {
            adapter = rvAdapter
            rv.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            rvAdapter.setData(resBody)
        }

        rvAdapter.setOnItemClickCallback(object : ListRvAdapter.IOnItemCallBack {
            override fun delete(id: IdModel) {
                val alertBuilder = AlertDialog.Builder(requireContext())
                alertBuilder.setPositiveButton("Yes") { _, _ ->
                    mViewModel.delete(id).observe(viewLifecycleOwner) {
                        HandlerApiClient.handle(it,requireContext(),object :HandlerApiClient.HandlerCallback{
                            override fun onSuccess() {
                                Toast.makeText(
                                    requireContext(),
                                    "Delete successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                    }

                }

                alertBuilder.setNegativeButton("No") { _, _ -> }
                alertBuilder.setTitle("Delete this?")
                alertBuilder.setMessage("Are you sure want to delete this")
                alertBuilder.create().show()
            }

            override fun update(data: PostModel) {
                val act = ListFragmentDirections.actionListFragmentToUpdateFragment(data)
                findNavController().navigate(act)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}