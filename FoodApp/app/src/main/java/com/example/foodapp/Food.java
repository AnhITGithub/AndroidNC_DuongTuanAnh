package com.example.foodapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table") // Tên bảng trong cơ sở dữ liệu
public class Food {

    @PrimaryKey(autoGenerate = true) // Đánh dấu trường này là khóa chính và tự động sinh giá trị
    private int id;
    private String name;
    private String description;

    // Constructor với id
    public Food(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Constructor không có id (để Room tự động sinh id)
//    public Food(String name, String description) {
//        this.name = name;
//        this.description = description;
//    }

    // Getter và Setter cho các trường
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
