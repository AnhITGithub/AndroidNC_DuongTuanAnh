package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private RecyclerView recyclerViewFood;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private FoodDao foodDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo database
        FoodDatabase db = ((App) requireActivity().getApplication()).getFoodDatabase();
        foodDao = db.foodDao();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerViewFood = view.findViewById(R.id.recyclerViewFood);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext()));
        foodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodList);
        recyclerViewFood.setAdapter(foodAdapter);

        // Thiết lập sự kiện click cho item trong RecyclerView
        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Food food = foodList.get(position);
                // Mở DetailFragment để hiển thị thông tin chi tiết
                Bundle bundle = new Bundle();
                bundle.putInt("food_id", food.getId());
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Load danh sách món ăn từ database
        loadFoods();
        return view;
    }

    private void loadFoods() {
        new Thread(() -> {
            foodList = foodDao.getAllFoods();
            requireActivity().runOnUiThread(() -> {
                foodAdapter.foodList.clear();
                foodAdapter.foodList.addAll(foodList);
                foodAdapter.notifyDataSetChanged();
            });
        }).start();
    }
}
