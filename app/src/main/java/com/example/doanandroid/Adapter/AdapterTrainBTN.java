package com.example.doanandroid.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Model.CLA_Model;
import com.example.doanandroid.Model.Tuition;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Object.TKBActivity;
import com.example.doanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTrainBTN extends RecyclerView.Adapter<AdapterTrainBTN.ViewHodel> {
    Context context;
    List<CLA_Model> mechanicalList;

    public AdapterTrainBTN(Context context, List<CLA_Model> mechanicalList) {
        this.context = context;
        this.mechanicalList = mechanicalList;

    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_btn, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, @SuppressLint("RecyclerView") int i) {
        holder.txt_title.setText(mechanicalList.get(i).getTitle());
        holder.txt_size.setVisibility(View.GONE);

        String title = mechanicalList.get(i).getLink().getContent_link();
        String link = mechanicalList.get(i).getLink().getLink();
        String size = mechanicalList.get(i).getLink().getSize();
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(mechanicalList.get(i).getLink()));
//                view.getContext().startActivity(intent);
                Bundle b = new Bundle();
                Intent i = new Intent(context, MainActivity.class);
                b.putString("title", title);
                b.putString("link", link);
                b.putString("size", size);
                i.putExtras(b);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mechanicalList.size() + TKBActivity.count_tkb;
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        TextView txt_title, txt_size;
        View v;
        public ViewHodel(@NonNull View view) {
            super(view);
            txt_title = view.findViewById(R.id.txt_title);
            txt_size  = view.findViewById(R.id.txt_size);
            v = view;
        }
    }

}
