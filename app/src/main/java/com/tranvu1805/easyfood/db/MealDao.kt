package com.tranvu1805.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tranvu1805.easyfood.models.Meal
@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(meal: Meal)
    @Delete
    suspend fun delete(meal: Meal)
    @Query("SELECT * FROM meals")
    fun getAllMeals(): LiveData<List<Meal>>
}