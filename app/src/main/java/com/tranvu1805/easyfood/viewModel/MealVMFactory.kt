package com.tranvu1805.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tranvu1805.easyfood.db.MealDatabase

@Suppress("UNCHECKED_CAST")
class MealVMFactory(
    private val mealDatabase: MealDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailViewModel(mealDatabase) as T
    }

}