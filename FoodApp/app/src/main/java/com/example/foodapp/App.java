package com.example.foodapp;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    private FoodDatabase foodDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        foodDatabase = Room.databaseBuilder(this, FoodDatabase.class, "food_db")
                .allowMainThreadQueries() // Cho phép truy vấn database trên main thread (chỉ sử dụng cho demo)
                .build();
    }

    public FoodDatabase getFoodDatabase() {
        return foodDatabase;
    }
}
