package com.tranvu1805.easyfood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tranvu1805.easyfood.databinding.CategoryItemBinding
import com.tranvu1805.easyfood.models.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoriesList = ArrayList<Category>()
    lateinit var onItemClickListener: ((Category) -> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setList(categoriesList: ArrayList<Category>) {
        this.categoriesList = categoriesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(categoriesList[position])
        }
    }

    class CategoryViewHolder(
        var binding: CategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

}