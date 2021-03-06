package com.example.doanandroid.Object;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.Fragment.HomeFragment;
import com.example.doanandroid.R;

import java.util.List;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> mobileCollection;
    private List<String> groupList;

    public MyExpandableListAdapter(Context context, List<String> groupList,
                                   Map<String, List<String>> mobileCollection) {
        this.context = context;
        this.mobileCollection = mobileCollection;
        this.groupList = groupList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mobileCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (mobileCollection.get(groupList.get(i)).size() == 0){
            return 0;
        }else
        return mobileCollection.get(groupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mobileCollection.get(groupList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String mobileName = getGroup(i).toString();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_item, null);
        }
        TextView item = view.findViewById(R.id.mobile);
        ImageView img = view.findViewById(R.id.img_view);
        RelativeLayout check = view.findViewById(R.id.relate_check);
        if (mobileName.equals("C??I ?????T") || mobileName.equals("THO??T")
                || mobileName.equals("TRANG CH???") || mobileName.equals("????NG NH???P")
                || mobileName.equals("H???C B???NG - VAY V???N")
                || mobileName.equals("H???C PH??")) {
            img.setVisibility(View.GONE);
        } else {
            img.setVisibility(View.VISIBLE);
        }
        if (mobileName.equals("TRANG CH???")) {
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout, new HomeFragment()).addToBackStack(null).commit();
        }
//        if (mobileName.equals("????NG NH???P")) {
//            view.getContext().startActivity(new Intent(context, LoginActivity.class));
//        }
//        if (mobileName.equals("THO??T")){
//            view.getContext().startActivity(new Intent(context, MainActivity.class));
//        }
        item.setTypeface(null, Typeface.BOLD);
        item.setText(mobileName);
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        String model = getChild(i, i1).toString();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child_item, null);
        }
        TextView item = view.findViewById(R.id.model);
        ImageView delete = view.findViewById(R.id.delete);
        item.setText(model);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        List<String> child = mobileCollection.get(groupList.get(i));
                        child.remove(i1);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
