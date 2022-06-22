package com.example.doanandroid.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Model.Tuition;
import com.example.doanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterScholarship extends RecyclerView.Adapter<AdapterScholarship.ViewHodel> {
    Context context;
    List<Tuition> mechanicalList;

    public AdapterScholarship(Context context, List<Tuition> mechanicalList) {
        this.context = context;
        this.mechanicalList = mechanicalList;

    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tuition, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, @SuppressLint("RecyclerView") int i) {
        holder.txt_title.setText(mechanicalList.get(i).getContent_link());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mechanicalList.get(i).getLink()));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mechanicalList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        TextView txt_title;
        View v;
        public ViewHodel(@NonNull View view) {
            super(view);
            txt_title = view.findViewById(R.id.txt_title);
            v = view;
        }
    }
    // Lọc Các Từ Khóa Gần Giống Khi search
    public void filterList(ArrayList<Tuition> filterllist) {
        mechanicalList = filterllist;
        notifyDataSetChanged();
    }
}
