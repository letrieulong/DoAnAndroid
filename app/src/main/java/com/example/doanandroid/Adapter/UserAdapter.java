package com.example.doanandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Model.post_care;
import com.example.doanandroid.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

//    public UserAdapter(List<post_care> mListUser) {
//        this.mListUser = mListUser;
//    }

    private List<post_care> mListUser;
    public void setData(List<post_care>list){
        this.mListUser=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_care,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        post_care Post_care = mListUser.get(position);
        if (Post_care==null){
            return;
        }
        holder.imgAvatar.setImageResource(Post_care.getResourceId());
        holder.title.setText(Post_care.getTitle());
        holder.date.setText(Post_care.getDate());
    }

    @Override
    public int getItemCount() {
        if(mListUser!=null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgAvatar;
        private TextView title, date;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_postcare);
            title = itemView.findViewById(R.id.txt_title_postcare);
            date = itemView.findViewById(R.id.txt_date_postcare);
        }
    }
}
