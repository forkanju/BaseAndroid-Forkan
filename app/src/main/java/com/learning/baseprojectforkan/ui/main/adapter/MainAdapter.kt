package com.learning.baseprojectforkan.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.baseprojectforkan.common.data.remote.model.User
import com.learning.baseprojectforkan.common.data.remote.model.UserModel
import com.learning.baseprojectforkan.databinding.ItemLayoutBinding

class MainAdapter(private val users: ArrayList<UserModel.UserModelItem>) :
    RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(var binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val user = users[position]
        holder.binding.textViewUserName.text = user.name
        holder.binding.textViewUserEmail.text = user.email
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun submitData(list: List<UserModel.UserModelItem>) {
        users.addAll(list)
    }

}