package com.example.foodapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private TextView nameTextView;
    private TextView descriptionTextView;
    private Button editButton;
    private Button deleteButton;
    private FoodDao foodDao;
    private int foodId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo database
        FoodDatabase db = ((App) requireActivity().getApplication()).getFoodDatabase();
        foodDao = db.foodDao();
        // Lấy ID món ăn từ arguments
        if (getArguments() != null) {
            foodId = getArguments().getInt("food_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        nameTextView = view.findViewById(R.id.nameTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        editButton = view.findViewById(R.id.editButton);
        deleteButton = view.findViewById(R.id.deleteButton);

        // Load thông tin món ăn từ database
        loadFood();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở EditFoodFragment để chỉnh sửa thông tin món ăn
                Bundle bundle = new Bundle();
                bundle.putInt("food_id", foodId);
                EditFoodFragment editFoodFragment = new EditFoodFragment();
                editFoodFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, editFoodFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác nhận xóa món ăn
                new DeleteFoodDialogFragment(foodId).show(getParentFragmentManager(), "delete_dialog");
            }
        });

        return view;
    }

    private void loadFood() {
        new Thread(() -> {
            Food food = foodDao.getFoodById(foodId);
            requireActivity().runOnUiThread(() -> {
                nameTextView.setText(food.getName());
                descriptionTextView.setText(food.getDescription());
            });
        }).start();
    }
}
