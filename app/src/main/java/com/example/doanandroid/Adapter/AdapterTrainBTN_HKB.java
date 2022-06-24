package com.example.doanandroid.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Model.CLA_Model;
import com.example.doanandroid.Model.HKB_Model;
import com.example.doanandroid.Object.MainActivity;
import com.example.doanandroid.Object.TKBActivity;
import com.example.doanandroid.R;

import java.util.List;

public class AdapterTrainBTN_HKB extends RecyclerView.Adapter<AdapterTrainBTN_HKB.ViewHodel> {
    Context context;
    List<HKB_Model> mechanicalList;

    public AdapterTrainBTN_HKB(Context context, List<HKB_Model> mechanicalList) {
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

        String content = mechanicalList.get(i).getContent();
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(mechanicalList.get(i).getLink()));
//                view.getContext().startActivity(intent);
                Bundle b = new Bundle();
                Intent i = new Intent(context, MainActivity.class);
                b.putString("content", content);
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
