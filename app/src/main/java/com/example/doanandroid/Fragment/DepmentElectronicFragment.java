package com.example.doanandroid.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.AdapterNew_Electronic;
import com.example.doanandroid.Adapter.AdapterNews_Electronic;
import com.example.doanandroid.Adapter.AdapterView_CNTT;
import com.example.doanandroid.MainActivity;
import com.example.doanandroid.Model.CNTT_infor;
import com.example.doanandroid.Model.Contact;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.News_Electronic;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DepmentElectronicFragment extends Fragment {
    public DepmentElectronicFragment() {
        // Required empty public constructor
    }
    RecyclerView recyNews;
    RecyclerView recyNew;
    ViewFlipper viewFlipper;
    AdapterNews_Electronic adapterNews_electronic;
    AdapterNew_Electronic adapterNew_electronic;
    List<News_Electronic> news_electronics = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_depment_electronic, container, false);
        init();
        Acviewflipper();
        getDataFireBase();
        setHasOptionsMenu(true);
        Actionbar();
        return view;
    }

    private void Actionbar() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void init() {
        viewFlipper = view.findViewById(R.id.viewflipper);

        // list new
        recyNew = view.findViewById(R.id.recynew);
        adapterNew_electronic = new AdapterNew_Electronic(getContext(), news_electronics);
        recyNew.setLayoutManager(new LinearLayoutManager(getContext()));
        recyNew.setAdapter(adapterNew_electronic);

        // list news
        recyNews = view.findViewById(R.id.recyNews);
        adapterNews_electronic = new AdapterNews_Electronic(getContext(), news_electronics);
        recyNews.setLayoutManager(new LinearLayoutManager(getContext()));
        recyNews.setAdapter(adapterNews_electronic);
    }

    // banner
    private void Acviewflipper() {
        ArrayList<String> arrayViewFlipper = new ArrayList<>();
        arrayViewFlipper.add("https://ddt.caothang.edu.vn/images/banner/1_10dc068cf61b0c45550a.jpg");
        arrayViewFlipper.add("https://ddt.caothang.edu.vn/images/banner/1641200410_Untitled-1.jpg");
        for (int i = 0 ; i < arrayViewFlipper.size(); i++){
            ImageView imageView = new ImageView(getContext());
            Glide.with(this).load(arrayViewFlipper.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); // sét hình ảnh hiển thị full;
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_right_out);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    // get data
    private void getDataFireBase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_electronic");
        // get data từ firebase
        mDatabase.child("list_news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("asd",snapshot.getValue().toString());
                news_electronics.clear();
                List<String> list = new ArrayList<>();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    list.add(dt.getKey());
                    News_Electronic rs = dt.getValue(News_Electronic.class);
                    news_electronics.add(rs);
                }
                adapterNew_electronic.notifyDataSetChanged();
                adapterNews_electronic.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get data từ firebase
        mDatabase.child("contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Contact rs = snapshot.getValue(Contact.class);

                setText(view.findViewById(R.id.txt_phone),rs.getPhone());
                setText(view.findViewById(R.id.txt_email),rs.getEmail());
                setText(view.findViewById(R.id.txt_address),rs.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Hỗ trợ đổi TEXT
    private void setText(final TextView text, final String value) {
        if (text != null) {
            text.setText(value);
        }
    }
    /**
     * Tìm kiếm
     * **/
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

//                    for (Recruit_CNTT rc : recruit_cnttList){
//                        if (rc.getTitle().toLowerCase().contains(newText.toLowerCase())){
//
//                        }
//                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
//                    for (Recruit_CNTT rc : recruit_cnttList){
//                        if (rc.getTitle().toLowerCase().contains(query.toLowerCase())){
//                            Log.d("abc", rc.getTitle());
//                        }
//                    }
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}