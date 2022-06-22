package com.example.doanandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Fragment.RoomAdminiStrativeFragment;
import com.example.doanandroid.Model.Recruit_Admin;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecruit_Admin extends RecyclerView.Adapter<AdapterRecruit_Admin.ViewHodel> {
    Context context;
    List<Recruit_Admin> recruit_admins;

    public AdapterRecruit_Admin(Context context, List<Recruit_Admin> recruit_admins) {
        this.context = context;
        this.recruit_admins = recruit_admins;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recruit, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int i) {
        holder.txt_date.setText("Ngày : " + recruit_admins.get(i).getDate());
        holder.txt_title.setText(recruit_admins.get(i).getTitle());
        holder.txt_title.setText(recruit_admins.get(i).getTitle());
        holder.txt_title.setMaxLines(2);
        Glide.with(context)
                .load(recruit_admins.get(i).getImage())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return recruit_admins.size() + RoomAdminiStrativeFragment.count_recruit;
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_title, txt_date;
        View v;
        public ViewHodel(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img_view);

            txt_title = view.findViewById(R.id.txt_title);
            txt_date  = view.findViewById(R.id.txt_date);
            v = view;
        }
    }
    // Lọc Các Từ Khóa Gần Giống Khi search
//    public void filterList(ArrayList<Recruit_CNTT> filterllist) {
//        recruit_cnttList = filterllist;
//        notifyDataSetChanged();
//    }
}
