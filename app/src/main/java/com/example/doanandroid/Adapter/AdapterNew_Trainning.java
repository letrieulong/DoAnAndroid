package com.example.doanandroid.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Fragment.RoomTrainingFragment;
import com.example.doanandroid.Model.ContentLink;
import com.example.doanandroid.Model.Mechanical;
import com.example.doanandroid.Model.New_Tranning;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterNew_Trainning extends RecyclerView.Adapter<AdapterNew_Trainning.ViewHodel> {
    Context context;
    List<New_Tranning> mechanicalList;
    AdapterNew_Trainning.ClickItemPost clickItemPost;
    public AdapterNew_Trainning(Context context, List<New_Tranning> mechanicalList, AdapterNew_Trainning.ClickItemPost clickItemPost) {
        this.context = context;
        this.mechanicalList = mechanicalList;
        this.clickItemPost= clickItemPost;

    }

    public interface ClickItemPost{
        void onClickItem(New_Tranning new_tranning);
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
        holder.txt_title.setEllipsize(TextUtils.TruncateAt.END);
        holder.txt_title.setMaxLines(2);
        holder.txt_title.setText(mechanicalList.get(i).getTitle());
//ihjihjkhkjhlj
        New_Tranning new_tranning = mechanicalList.get(i);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemPost.onClickItem(new_tranning);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mechanicalList.size() + RoomTrainingFragment.count_new;
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
//    public void filterList(ArrayList<Mechanical> filterllist) {
//        mechanicalList = filterllist;
//        notifyDataSetChanged();
//    }
}
