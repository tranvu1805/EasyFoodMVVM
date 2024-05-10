package com.tranvu1805.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tranvu1805.easyfood.models.MealByCategory
import com.tranvu1805.easyfood.models.MealListByCategory
import com.tranvu1805.easyfood.retrofit.RetrofitObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivityVM: ViewModel() {
    private var _mealsLiveData = MutableLiveData<List<MealByCategory>>()
    val mealsLiveData get() = _mealsLiveData
    fun getMealsByCategory(category: String){
        RetrofitObject.api.getCategoryItems(category).enqueue(object : Callback<MealListByCategory>{
            override fun onResponse(
                p0: Call<MealListByCategory>,
                p1: Response<MealListByCategory>
            ) {
                p1.body()?.let {
                    _mealsLiveData.postValue(it.meals)
                }
            }

            override fun onFailure(p0: Call<MealListByCategory>, p1: Throwable) {
                Log.d("CategoryActivityVM", "onFailure: $p1")
            }
        })
    }
}