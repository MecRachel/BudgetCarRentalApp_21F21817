package com.example.budgetapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RachelCarAdapter extends RecyclerView.Adapter<RachelCarAdapter.CarViewHolder> {

    private Context mContext21F21817;
    private List<RachelCar> carList;

    public RachelCarAdapter(Context mContext, List<RachelCar> carList) {
        this.mContext21F21817 = mContext;
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext21F21817).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        RachelCar car = carList.get(position);
        holder.tv21F21817Model.setText(car.getModel());
        holder.tv21F21817RegistrationNumber.setText(car.getRegistrationNo());
        holder.tv21F21817Brand.setText(car.getBrand());
        holder.tv21F21817PricePerDay.setText(String.format("OMR %.2f/day", car.getPrice()));

        holder.iv21F21817CarImage.setImageResource(car.getImageResId());

        holder.btn21F21817BookCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext21F21817, ManageRachelBookingScreen.class);
                intent.putExtra("carModel", car.getModel());
                intent.putExtra("pricePerDay", car.getPrice());
                mContext21F21817.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView tv21F21817Model, tv21F21817RegistrationNumber, tv21F21817Brand, tv21F21817PricePerDay;
        ImageView iv21F21817CarImage;
        Button btn21F21817BookCar;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tv21F21817Model = itemView.findViewById(R.id.tvModel);
            tv21F21817RegistrationNumber = itemView.findViewById(R.id.tvRegistrationNumber);
            tv21F21817Brand = itemView.findViewById(R.id.tvBrand);
            tv21F21817PricePerDay = itemView.findViewById(R.id.tvPricePerDay);
            iv21F21817CarImage = itemView.findViewById(R.id.ivCarImage);
            btn21F21817BookCar = itemView.findViewById(R.id.btnBookCar);
        }
    }
}
