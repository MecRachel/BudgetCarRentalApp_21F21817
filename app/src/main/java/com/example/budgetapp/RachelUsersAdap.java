package com.example.budgetapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class RachelUsersAdap extends RecyclerView.Adapter<RachelUsersAdap.UserViewHolder> {


    private final Context context21F21817;
    private final List<RachelJava> userListRachie;

    public RachelUsersAdap(Context context, List<RachelJava> userList) {
        this.context21F21817 = context;
        this.userListRachie = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context21F21817).inflate(R.layout.itemracheluser, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        RachelJava user = userListRachie.get(position);
        holder.RRName.setText(user.getName());
        holder.RREmail.setText(user.getEmail());
        holder.RRContNum.setText(user.getContactNumber());
    }

    @Override
    public int getItemCount() {
        return userListRachie.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView RRName, RREmail, RRContNum;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            RRName = itemView.findViewById(R.id.User20Name);
            RREmail = itemView.findViewById(R.id.User20Email);
            RRContNum = itemView.findViewById(R.id.User20ContactNum);
        }
    }
}

