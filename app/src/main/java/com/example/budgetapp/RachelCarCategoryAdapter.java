package com.example.budgetapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RachelCarCategoryAdapter extends RecyclerView.Adapter<RachelCarCategoryAdapter.CarCategoryViewHolder> {

    private Context mContext;
    private List<RachelCarCategory> carCategoryList;

    public RachelCarCategoryAdapter(Context mContext, List<RachelCarCategory> carCategoryList) {
        this.mContext = mContext;
        this.carCategoryList = carCategoryList;
    }

    @NonNull
    @Override
    public CarCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_car_category, parent, false);
        return new CarCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarCategoryViewHolder holder, int position) {
        RachelCarCategory carCategory = carCategoryList.get(position);
        holder.categoryName.setText(carCategory.getCategoryName());

        holder.recyclerViewCars.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        RachelCarAdapter carAdapter = new RachelCarAdapter(mContext, carCategory.getCars());
        holder.recyclerViewCars.setAdapter(carAdapter);
    }

    @Override
    public int getItemCount() {
        return carCategoryList.size();
    }

    public static class CarCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView recyclerViewCars;

        public CarCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tvCategoryName);
            recyclerViewCars = itemView.findViewById(R.id.recyclerViewCars);
        }
    }
}
