package com.tranvu1805.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranvu1805.easyfood.db.MealDatabase
import com.tranvu1805.easyfood.models.Meal
import com.tranvu1805.easyfood.models.MealList
import com.tranvu1805.easyfood.retrofit.RetrofitObject
import kotlinx.coroutines.launch
import retrofit2.Call

class MealDetailViewModel(
    val mealDatabase: MealDatabase
) : ViewModel() {
    private val _mealLiveData = MutableLiveData<Meal>()
    val mealLiveData: LiveData<Meal>
        get() = _mealLiveData

    fun getMealDetail(id: String) {
        RetrofitObject.api.getMealDetails(id).enqueue(object : retrofit2.Callback<MealList> {
            override fun onResponse(
                call: Call<MealList>,
                response: retrofit2.Response<MealList>
            ) {
                if (response.body() != null)
                    _mealLiveData.value = response.body()!!.meals[0]
                else
                    return
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.d("MealDetail", p1.message.toString())

            }
        })
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
}