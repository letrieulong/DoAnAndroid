package com.example.doanandroid.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecruit_CNTT extends RecyclerView.Adapter<AdapterRecruit_CNTT.ViewHodel> {
    Context context;
    List<Recruit_CNTT> recruit_cnttList;
    ClickItemPost clickItemPost;
    public AdapterRecruit_CNTT(Context context, List<Recruit_CNTT> recruit_cnttList, ClickItemPost clickItemPost) {
        this.context = context;
        this.clickItemPost = clickItemPost;
        this.recruit_cnttList = recruit_cnttList;
    }

    public interface ClickItemPost{
        void onClickItem(Recruit_CNTT recruit_cntt);
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
        holder.txt_date.setText("Ngày : " + recruit_cnttList.get(i).getDate());
        holder.txt_title.setText(recruit_cnttList.get(i).getTitle());
        Glide.with(context)
                .load(recruit_cnttList.get(i).getImage())
                .into(holder.img);

        Recruit_CNTT recruit_cntt = recruit_cnttList.get(i);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemPost.onClickItem(recruit_cntt);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recruit_cnttList.size();
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
    public void filterList(ArrayList<Recruit_CNTT> filterllist) {
        recruit_cnttList = filterllist;
        notifyDataSetChanged();
    }
}
