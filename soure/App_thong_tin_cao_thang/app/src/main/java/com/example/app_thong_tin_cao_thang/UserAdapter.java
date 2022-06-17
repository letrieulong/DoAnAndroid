package com.example.app_thong_tin_cao_thang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<user> mListUser;

    public UserAdapter(List<user> mListUser) {
        this.mListUser = mListUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        user user =mListUser.get(position);
        if(user==null){
            return;
        }
        holder.img_avatar.setImageResource(user.getResourceId());
        holder.txt_namepost.setText(user.getName());
        holder.txt_view.setText(user.getView());
        holder.txt_ngay.setText(user.getData());
    }

    @Override
    public int getItemCount() {
        if(mListUser!=null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_avatar;
        private TextView txt_namepost,txt_view, txt_ngay;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.img_avatar);
            txt_namepost=itemView.findViewById(R.id.txt_namepost);
            txt_view=itemView.findViewById(R.id.txt_view);
            txt_ngay=itemView.findViewById(R.id.txt_ngay);
        }
    }
}
