package com.tranvu1805.easyfood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tranvu1805.easyfood.databinding.PopularItemBinding
import com.tranvu1805.easyfood.models.MealByCategory

class PopularItemAdapter : RecyclerView.Adapter<PopularItemAdapter.ViewHolder>() {
    lateinit var onItemClick: ((MealByCategory) -> Unit)
    var onLongClickItem: ((MealByCategory) -> Unit)? = null
    private var itemList = ArrayList<MealByCategory>()

    class ViewHolder(val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: ArrayList<MealByCategory>) {
        this.itemList = itemList
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(itemList[position].strMealThumb)
            .into(holder.binding.imgPopular)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(itemList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongClickItem?.invoke(itemList[position])
            true
        }
    }

}