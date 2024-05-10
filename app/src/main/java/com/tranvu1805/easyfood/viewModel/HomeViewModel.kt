package com.tranvu1805.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranvu1805.easyfood.db.MealDatabase
import com.tranvu1805.easyfood.models.Category
import com.tranvu1805.easyfood.models.CategoryList
import com.tranvu1805.easyfood.models.Meal
import com.tranvu1805.easyfood.models.MealByCategory
import com.tranvu1805.easyfood.models.MealList
import com.tranvu1805.easyfood.models.MealListByCategory
import com.tranvu1805.easyfood.retrofit.RetrofitObject
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {
    private var _randomMealLiveData = MutableLiveData<Meal>()
    private var _popularItemsLiveData = MutableLiveData<List<MealByCategory>>()
    private var _categoryLiveData = MutableLiveData<List<Category>>()
    private var _favoriteMeal = mealDatabase.mealDao().getAllMeals()
    private var _bottomSheetMealLiveData = MutableLiveData<Meal>()
    private var _mealBySearch = MutableLiveData<List<Meal>>()

    val randomMealLiveData: LiveData<Meal> get() = _randomMealLiveData
    val popularItemsLiveData: LiveData<List<MealByCategory>> get() = _popularItemsLiveData
    val categoryLiveData: LiveData<List<Category>> get() = _categoryLiveData
    val favoriteMeal: LiveData<List<Meal>> get() = _favoriteMeal
    val bottomSheetMealLiveData: LiveData<Meal> get() = _bottomSheetMealLiveData
    val mealBySearch: LiveData<List<Meal>> get() = _mealBySearch

    init {
        getRandomMeal()
    }
//    private var saveRandomMeal: Meal? = null
    private fun getRandomMeal() {
//        saveRandomMeal?.let {
//            _randomMealLiveData.postValue(it)
//            return
//        }
        RetrofitObject.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val meal = response.body()!!.meals[0]
                    _randomMealLiveData.value = meal
//                    saveRandomMeal = meal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment1", t.message.toString())
            }
        })
    }

    fun getPopularItems(categoryName: String) {
        RetrofitObject.api.getPopularItems(categoryName)
            .enqueue(object : Callback<MealListByCategory> {
                override fun onResponse(
                    p0: Call<MealListByCategory>,
                    p1: Response<MealListByCategory>
                ) {
                    if (p1.body() != null) {
                        _popularItemsLiveData.value = p1.body()!!.meals
                    } else {
                        return
                    }
                }

                override fun onFailure(p0: Call<MealListByCategory>, p1: Throwable) {
                    Log.d("HomeFragment2", p1.message.toString())
                }

            })
    }


    fun getCategory() {
        RetrofitObject.api.getCategory().enqueue(object : Callback<CategoryList> {
            override fun onResponse(p0: Call<CategoryList>, p1: Response<CategoryList>) {
                p1.body()?.let {
                    _categoryLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(p0: Call<CategoryList>, p1: Throwable) {

            }

        })
    }

    fun getMealById(id: String) {
        RetrofitObject.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                val meal = p1.body()?.meals?.first()
                meal?.let {
                    _bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {

            }

        })
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun searchMeal(searchQuery: String) {
        RetrofitObject.api.searchMeal(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                val meals = p1.body()?.meals
                meals?.let {
                    _mealBySearch.postValue(it)
                }
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.d("HomeFragment3", p1.message.toString())
            }
        })
    }
}