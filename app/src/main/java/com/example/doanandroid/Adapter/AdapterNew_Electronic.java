package com.example.doanandroid.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Fragment.DepmentElectronicFragment;
import com.example.doanandroid.Model.News_Electronic;
import com.example.doanandroid.R;

import java.util.List;

public class AdapterNew_Electronic extends RecyclerView.Adapter<AdapterNew_Electronic.ViewHodel> {
    Context context;
    List<News_Electronic> news_electronics;

    public AdapterNew_Electronic(Context context, List<News_Electronic> news_electronics) {
        this.context = context;
        this.news_electronics = news_electronics;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_new, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int i) {
        holder.txt_title.setMaxLines(3);
        holder.txt_title.setEllipsize(TextUtils.TruncateAt.END);
        holder.txt_title.setText(news_electronics.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return news_electronics.size() + DepmentElectronicFragment.count;
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
//    public void filterList(ArrayList<Recruit_CNTT> filterllist) {
//        cntt_inforList = filterllist;
//        notifyDataSetChanged();
//    }
}
