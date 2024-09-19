package com.example.foodapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditFoodFragment extends Fragment {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Button saveButton;
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
        View view = inflater.inflate(R.layout.fragment_edit_food, container, false);
        nameEditText = view.findViewById(R.id.nameEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        saveButton = view.findViewById(R.id.saveButton);

        // Load thông tin món ăn từ database
        loadFood();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                if (!name.isEmpty() && !description.isEmpty()) {
                    // Cập nhật thông tin món ăn trong database
                    Food food = new Food(foodId, name, description);
                    new Thread(() -> {
                        foodDao.updateFood(food);
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
                            getParentFragmentManager().popBackStack();
                        });
                    }).start();
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void loadFood() {
        new Thread(() -> {
            Food food = foodDao.getFoodById(foodId);
            requireActivity().runOnUiThread(() -> {
                nameEditText.setText(food.getName());
                descriptionEditText.setText(food.getDescription());
            });
        }).start();
    }
}
