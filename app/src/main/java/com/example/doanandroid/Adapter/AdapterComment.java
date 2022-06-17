package com.example.doanandroid.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Model.Comment;
import com.example.doanandroid.Model.New_Tranning;
import com.example.doanandroid.R;

import java.util.List;

public class AdapterComment  extends RecyclerView.Adapter<AdapterComment.ViewHodel> {

    List<Comment> comments;

    public AdapterComment(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        return new AdapterComment.ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {

        holder.txt_name.setText("Tên : " + comments.get(position).getNameUser());
        holder.txt_content.setText("Nội dung : " + comments.get(position).getContent());
        holder.txt_date.setText("Ngày : " + comments.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_content, txt_date, txt_name;
        View v;
        public ViewHodel(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img_user);

            txt_name = view.findViewById(R.id.tv_name_user_comment);
            txt_content  = view.findViewById(R.id.tv_content_comment);
            txt_date  = view.findViewById(R.id.tv_date_comment);
            v = view;
        }
    }
}
