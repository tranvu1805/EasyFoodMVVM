package com.tranvu1805.easyfood.retrofit

import com.tranvu1805.easyfood.models.CategoryList
import com.tranvu1805.easyfood.models.MealList
import com.tranvu1805.easyfood.models.MealListByCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName: String): Call<MealListByCategory>

    @GET("categories.php")
    fun getCategory(): Call<CategoryList>

    @GET("filter.php")
    fun getCategoryItems(@Query("c") categoryName: String): Call<MealListByCategory>

    @GET("search.php?")
    fun searchMeal(@Query("s") searchQuery: String): Call<MealList>

}