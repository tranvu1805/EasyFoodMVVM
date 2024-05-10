package com.tranvu1805.easyfood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tranvu1805.easyfood.databinding.CategoryMealItemBinding
import com.tranvu1805.easyfood.models.MealByCategory

class MealByCategoryAdapter :
    RecyclerView.Adapter<MealByCategoryAdapter.MealByCategoryViewHolder>() {
    private var mealList = ArrayList<MealByCategory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealList: List<MealByCategory>) {
        this.mealList = mealList as ArrayList<MealByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealByCategoryViewHolder {
        return MealByCategoryViewHolder(
            CategoryMealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MealByCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class MealByCategoryViewHolder(var binding: CategoryMealItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}