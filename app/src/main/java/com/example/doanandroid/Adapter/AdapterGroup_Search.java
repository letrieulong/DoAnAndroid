package com.example.doanandroid.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Model.New_Tranning;
import com.example.doanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterGroup_Search extends RecyclerView.Adapter<AdapterGroup_Search.ViewHodel> {
    Context context;
    List<New_Tranning> mechanicalList;

    public AdapterGroup_Search(Context context, List<New_Tranning> mechanicalList) {
        this.context = context;
        this.mechanicalList = mechanicalList;

    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, @SuppressLint("RecyclerView") int i) {
        holder.txt_title.setEllipsize(TextUtils.TruncateAt.END);
        holder.txt_title.setMaxLines(2);
        holder.txt_title.setText(mechanicalList.get(i).getTitle());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, mechanicalList.get(i).getTitle()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mechanicalList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_title;
        View v;
        public ViewHodel(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img_view);

            txt_title = view.findViewById(R.id.txt_title);
            v = view;
        }
    }
    // L???c C??c T??? Kh??a G???n Gi???ng Khi search
    public void filterList(ArrayList<New_Tranning> filterllist) {
        mechanicalList = filterllist;
        notifyDataSetChanged();
    }
}
