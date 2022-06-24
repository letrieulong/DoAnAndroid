package com.example.doanandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Adapter.UserAdapter;
import com.example.doanandroid.Model.post_care;
import com.example.doanandroid.R;

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
    private UserAdapter mUserAdapter;
    private List<post_care> mListUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bai_quan_tam_nhieu_, container, false);

        rcvUser = mView.findViewById(R.id.rcv_user);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        rcvUser.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration= new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(itemDecoration);

//        mListUser=new ArrayList<>();
        mUserAdapter = new UserAdapter();
        mUserAdapter.setData(getListUser());

        rcvUser.setAdapter(mUserAdapter);
        return mView;
    }

    private void getListUser_data(){

    }
    private List<post_care> getListUser() {

        List<post_care> list = new ArrayList<>();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef=database.getReference("list_cntt");
//        myRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    post_care Post_care  = dataSnapshot.getValue(post_care.class);
//                    list.add(Post_care);
//                }
////                mUserAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(),"Sai",Toast.LENGTH_SHORT).show();
//            }
//        });

        list.add(new post_care(R.drawable.logocaothang,"[TUYỂN DỤNG] - CÔNG TY TNHH SẢN XUẤT FIRST SOLAR VIỆT NAM","10/06/2022"));
        list.add(new post_care(R.drawable.logocaothang,"[TUYỂN DỤNG] - CÔNG TY TNHH QUỐC TẾ GMB","31/05/2022"));
        list.add(new post_care(R.drawable.logocaothang ,"[TUYỂN DỤNG] - KỸ THUẬT VIÊN QA/QC CÔNG TY TNHH SV PROBE VIETNAM","25/05/2022"));
        return list;
    }
}