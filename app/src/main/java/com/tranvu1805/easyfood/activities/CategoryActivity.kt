package com.tranvu1805.easyfood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tranvu1805.easyfood.R
import com.tranvu1805.easyfood.adapters.MealByCategoryAdapter
import com.tranvu1805.easyfood.databinding.ActivityCategoryBinding
import com.tranvu1805.easyfood.fragments.HomeFragment
import com.tranvu1805.easyfood.models.MealByCategory
import com.tranvu1805.easyfood.viewModel.CategoryActivityVM

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryName: String
    private lateinit var categoryActivityVM: CategoryActivityVM
    private lateinit var mealByCategoryAdapter: MealByCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategoryName()
        categoryActivityVM = ViewModelProvider(this)[CategoryActivityVM::class.java]
        categoryActivityVM.getMealsByCategory(categoryName)
        setInfoToView()
        prepareAdapter()

    }

    private fun setInfoToView() {
        categoryActivityVM.mealsLiveData.observe(this) {
            mealByCategoryAdapter.setMeals(it as ArrayList<MealByCategory>)
            binding.tvNumber.text = getString(R.string.number_of_meals, it.size.toString())
        }
    }

    private fun prepareAdapter() {
        binding.rvCategory.apply {
            mealByCategoryAdapter = MealByCategoryAdapter()
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealByCategoryAdapter
        }
    }

    private fun getCategoryName() {
        categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME).toString()
    }
}