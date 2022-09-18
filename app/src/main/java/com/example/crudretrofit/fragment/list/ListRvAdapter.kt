package com.example.crudretrofit.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.crudretrofit.R
import com.example.crudretrofit.fragment.update.UpdateFragmentDirections
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel

// refresh rv
// https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data

class ListRvAdapter(private var rvList:List<PostModel>) : RecyclerView.Adapter<ListRvAdapter.ViewHolder>() {
    private lateinit var onItemCallBack:IOnItemCallBack

    class ViewHolder(it: View):RecyclerView.ViewHolder(it) {
        val tvRv:TextView = it.findViewById(R.id.textView)
        val btnDelete:Button = it.findViewById(R.id.btn_delete)
        val btnUpdate:Button = it.findViewById(R.id.btn_update)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rvList[position]
        val text = "${position + 1}. ${ item.title}"

        holder.tvRv.text = text
        holder.btnDelete.setOnClickListener {
            val id = IdModel(item._id!!)
            onItemCallBack.delete(id,holder.adapterPosition)
        }

        holder.btnUpdate.setOnClickListener {
            val act = ListFragmentDirections.actionListFragmentToUpdateFragment(item)
            holder.itemView.findNavController().navigate(act)
        }
    }

    override fun getItemCount(): Int {
       return rvList.size
    }

    fun setOnItemClickCallback(action:IOnItemCallBack){
    this.onItemCallBack = action
    }

    interface IOnItemCallBack{
        fun delete(id:IdModel,position:Int)
    }

}