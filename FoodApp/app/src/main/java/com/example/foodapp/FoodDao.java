package com.example.foodapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insertFood(Food food);

    @Update
    void updateFood(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("SELECT * FROM food_table")
    List<Food> getAllFoods();

    @Query("SELECT * FROM food_table WHERE id = :foodId")
    Food getFoodById(int foodId);  // Thêm phương thức này
}
