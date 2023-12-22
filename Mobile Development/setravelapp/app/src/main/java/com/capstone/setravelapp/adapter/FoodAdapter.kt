package com.capstone.setravelapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.setravelapp.data.remote.response.FoodResponseItem
import com.capstone.setravelapp.databinding.MakananBinding
import com.capstone.setravelapp.view.activity.detailmakanan.DetailMakananActivity

class FoodAdapter : ListAdapter<FoodResponseItem, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = MakananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

    }
    

    class FoodViewHolder(private val binding: MakananBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodItem: FoodResponseItem) {
            binding.apply {
                tvName.text = foodItem.namaKuliner
                tvDecription.text = foodItem.deskripsi
                Glide.with(itemView)
                    .load(foodItem.imageUrl)
                    .centerCrop()
                    .into(ivFotomakan)

                //option compat
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMakananActivity::class.java)
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(ivFotomakan, "imageUrl"),
                            Pair(tvName, "name"),
                            Pair(tvDecription, "deskripsi"),
                        )
                    intent.putExtra(DetailMakananActivity.EXTRA_FOOD_ITEM, foodItem)
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    private class FoodDiffCallback : DiffUtil.ItemCallback<FoodResponseItem>() {
        override fun areItemsTheSame(oldItem: FoodResponseItem, newItem: FoodResponseItem): Boolean {
            return oldItem.namaKuliner == newItem.namaKuliner
        }

        override fun areContentsTheSame(oldItem: FoodResponseItem, newItem: FoodResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}