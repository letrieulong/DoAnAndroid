package com.example.doanandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Model.DemoModel;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHodel> {
    Context context;
    List<Recruit_CNTT> demoModelList;

    public AdapterSearch(Context context, List<Recruit_CNTT> demoModelList) {
        this.context = context;
        this.demoModelList = demoModelList;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int i) {
        holder.txt_date.setText("Ngày : " + demoModelList.get(i).getDate());
        holder.txt_title.setText(demoModelList.get(i).getTitle());
        holder.txt_view.setText("View : " + demoModelList.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return demoModelList.size();
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
    public void filterList(ArrayList<Recruit_CNTT> filterllist) {
        demoModelList = filterllist;
        notifyDataSetChanged();
    }
}
