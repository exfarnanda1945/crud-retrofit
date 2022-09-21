package com.example.crudretrofit.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crudretrofit.databinding.RvRowBinding
import com.example.crudretrofit.models.IdModel
import com.example.crudretrofit.models.PostModel

// refresh rv
// https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data

class ListRvAdapter : RecyclerView.Adapter<ListRvAdapter.ViewHolder>() {

    private val diffCallBack=object: DiffUtil.ItemCallback<PostModel>(){
        override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)

    private lateinit var onItemCallBack:IOnItemCallBack

    inner class ViewHolder(itemBinding: RvRowBinding):RecyclerView.ViewHolder(itemBinding.root) {
        val tvRv:TextView = itemBinding.textView
        val btnDelete:Button = itemBinding.btnDelete
        val btnUpdate:Button = itemBinding.btnUpdate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = RvRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        val text = "${position + 1}. ${ item.title}"

        holder.tvRv.text = text
        holder.btnDelete.setOnClickListener {
            val id = IdModel(item._id!!)
            onItemCallBack.delete(id)
        }

        holder.btnUpdate.setOnClickListener {
            onItemCallBack.update(item)
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    fun setData(list:List<PostModel>?){
        differ.submitList(list)
    }


    fun setOnItemClickCallback(action:IOnItemCallBack){
    this.onItemCallBack = action
    }

    interface IOnItemCallBack{
        fun delete(id:IdModel)
        fun update(data:PostModel)
    }

}