package com.example.budgetapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final Context context21F21817;
    private final List<RachelBookingClass> bookingList21F21817;
    private final DatabaseReference databaseRefer21F2;

    public BookingAdapter(Context contextRachie, List<RachelBookingClass> bookingListRachie, DatabaseReference databaseRefRachie) {
        this.context21F21817 = contextRachie;
        this.bookingList21F21817 = bookingListRachie;
        this.databaseRefer21F2 = databaseRefRachie;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context21F21817).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        RachelBookingClass booking = bookingList21F21817.get(position);
        holder.t21F21817CarModel.setText(booking.getCarModel());
        holder.t21F21817BookingDates.setText(booking.getStartDate() + " - " + booking.getEndDate());

        holder.b21F21817tnEdit.setOnClickListener(v -> {
            if (context21F21817 instanceof RachelViewBookingScreen) {
                ((RachelViewBookingScreen) context21F21817).editBooking(booking);
            }
        });

        holder.b21F21817tnDelete.setOnClickListener(v -> {
            databaseRefer21F2.child(booking.getBookingId()).removeValue();
            bookingList21F21817.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return bookingList21F21817.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView t21F21817CarModel, t21F21817BookingDates;
        Button b21F21817tnEdit, b21F21817tnDelete;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            t21F21817CarModel = itemView.findViewById(R.id.tvCarModel);
            t21F21817BookingDates = itemView.findViewById(R.id.tvBookingDates);
            b21F21817tnEdit = itemView.findViewById(R.id.btnEdit);
            b21F21817tnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
