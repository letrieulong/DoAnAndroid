package com.example.doanandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterNotification_Mechanical extends RecyclerView.Adapter<AdapterNotification_Mechanical.ViewHodel> {
    Context context;
    List<Mechanical> mechanicalList;

    public AdapterNotification_Mechanical(Context context, List<Mechanical> mechanicalList) {
        this.context = context;
        this.mechanicalList = mechanicalList;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_mechanical, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int i) {
        holder.txt_date.setText("Ngày : " + mechanicalList.get(i).getDate());
        holder.txt_title.setText(mechanicalList.get(i).getTitle());
        holder.txt_view.setText("Lượt xem : " + mechanicalList.get(i).getViews());
    }

    @Override
    public int getItemCount() {
        return mechanicalList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_title, txt_date, txt_view;
        View v;
        public ViewHodel(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img_view);

            txt_title = view.findViewById(R.id.txt_title);
            txt_date  = view.findViewById(R.id.txt_date);
            txt_view  = view.findViewById(R.id.txt_view);
            v = view;
        }
    }
    // Lọc Các Từ Khóa Gần Giống Khi search
    public void filterList(ArrayList<Mechanical> filterllist) {
        mechanicalList = filterllist;
        notifyDataSetChanged();
    }
}
