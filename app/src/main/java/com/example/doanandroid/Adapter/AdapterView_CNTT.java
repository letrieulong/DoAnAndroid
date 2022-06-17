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
import com.example.doanandroid.Model.CNTT_infor;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterView_CNTT extends RecyclerView.Adapter<AdapterView_CNTT.ViewHodel> {
    Context context;
    List<Infor_All_CNTT> infor_all_cntts;
    AdapterView_CNTT.ClickItemPost clickItemPost;

    public AdapterView_CNTT(Context context, List<Infor_All_CNTT> infor_all_cntts,  AdapterView_CNTT.ClickItemPost clickItemPost) {
        this.context = context;
        this.infor_all_cntts = infor_all_cntts;
        this.clickItemPost = clickItemPost;
    }
    public interface ClickItemPost{
        void onClickItemInfor(Infor_All_CNTT infor_all_cntt);
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
        holder.txt_date.setText("Ngày : " + infor_all_cntts.get(i).getDate());
        holder.txt_title.setMaxLines(3);
        holder.txt_title.setEllipsize(TextUtils.TruncateAt.END);
        holder.txt_title.setText(infor_all_cntts.get(i).getTitle());

        Infor_All_CNTT infor_all_cntt = infor_all_cntts.get(i);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemPost.onClickItemInfor(infor_all_cntt);
            }
        });
        Glide.with(context)
                .load(infor_all_cntts.get(i).getImage())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return infor_all_cntts.size();
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
//    public void filterList(ArrayList<Recruit_CNTT> filterllist) {
//        cntt_inforList = filterllist;
//        notifyDataSetChanged();
//    }
}
