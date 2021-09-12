package com.sayyidisal.foodorderingapp.ingredient.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sayyidisal.foodorderingapp.ingredient.entities.CategoryItems
import com.sayyidisal.foodorderingapp.ingredient.entities.MealsItems

@Dao
interface RecipeDao {

    @Query("SELECT * FROM categoryitems ORDER BY id DESC")
    suspend fun getAllCategory() : List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: CategoryItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsItems: MealsItems?)

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()

    @Query("SELECT * FROM MealItems WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName:String) : List<MealsItems>
}