package com.example.doanandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Adapter.QuanTamAdapter;
import com.example.doanandroid.Adapter.UserAdapter;
import com.example.doanandroid.Model.post_care;
import com.example.doanandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bai_quan_tam_nhieu_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bai_quan_tam_nhieu_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Bai_quan_tam_nhieu_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bai_quan_tam_nhieu_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Bai_quan_tam_nhieu_Fragment newInstance(String param1, String param2) {
        Bai_quan_tam_nhieu_Fragment fragment = new Bai_quan_tam_nhieu_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView rcvUser;
    private View mView;
    private List<post_care> mListUser = new ArrayList<>();;
    private QuanTamAdapter userAdapter;
    private TextView txt_view_more;
    public static int count = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bai_quan_tam_nhieu_, container, false);
        initUi();
        getListPost();

        return mView;
    }

    private void initUi(){
        rcvUser = mView.findViewById(R.id.rcv_user);

        txt_view_more= mView.findViewById(R.id.view_more);


        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        rcvUser.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration= new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(itemDecoration);

        userAdapter = new QuanTamAdapter(mListUser);
        rcvUser.setAdapter(userAdapter);
    }

    private void getListPost() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("list_group");
        myRef.child("list_group_student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> list = new ArrayList<>();
                //cla_modelList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    post_care n = snap.getValue(post_care.class);
                    mListUser.add(n);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_more:
                if (txt_view_more.getText().toString().equals("Xem th??m")) {
                    if (mListUser.size() + count < mListUser.size()) {
                        count++;
                        userAdapter.notifyDataSetChanged();
                        if (mListUser.size() + count == mListUser.size()) {
                            txt_view_more.setText("Thu nh???");
                        }
                    }
                } else {
                    count = -1;
                    txt_view_more.setText("Xem th??m");
                    userAdapter.notifyDataSetChanged();
                }

                return;
        }
    }
}