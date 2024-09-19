package com.example.foodapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteFoodDialogFragment extends DialogFragment {

    private int foodId;
    private FoodDao foodDao;

    public DeleteFoodDialogFragment(int foodId) {
        this.foodId = foodId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Khởi tạo database
        FoodDatabase db = ((App) requireActivity().getApplication()).getFoodDatabase();
        foodDao = db.foodDao();

        // Tạo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa món ăn này?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xóa món ăn khỏi database
                        new Thread(() -> {
                            foodDao.deleteFood(new Food(foodId, "", ""));
                            requireActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
                                getParentFragmentManager().popBackStack();
                            });
                        }).start();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Không làm gì
                    }
                });
        return builder.create();
    }
}
