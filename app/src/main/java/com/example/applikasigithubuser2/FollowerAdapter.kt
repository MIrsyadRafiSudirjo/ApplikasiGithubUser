package com.example.applikasigithubuser2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applikasigithubuser2.databinding.ItemRowUserBinding

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    private val mData = ArrayList<FollowerItems>()
    fun setData(items: ArrayList<FollowerItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(userItems: FollowerItems) {
            Glide.with(itemView.context)
                    .load(userItems.avatarfwr)
                    .into(binding.avatarImage)
            binding.tvUsername.text = userItems.namefwr
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowerAdapter.FollowerViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return FollowerAdapter.FollowerViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowerAdapter.FollowerViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}