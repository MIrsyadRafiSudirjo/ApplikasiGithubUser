package com.example.applikasigithubuser2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applikasigithubuser2.databinding.ItemRowUserBinding

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val mData = ArrayList<FollowingItems>()
    fun setData(items: ArrayList<FollowingItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(userItems: FollowingItems) {
            Glide.with(itemView.context)
                    .load(userItems.avatarfwl)
                    .into(binding.avatarImage)
            binding.tvUsername.text = userItems.namefwl
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowingAdapter.FollowingViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return FollowingAdapter.FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.FollowingViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

}