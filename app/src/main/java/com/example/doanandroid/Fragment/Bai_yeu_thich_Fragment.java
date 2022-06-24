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
 * Use the {@link Bai_yeu_thich_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bai_yeu_thich_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Bai_yeu_thich_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bai_yeu_thich_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Bai_yeu_thich_Fragment newInstance(String param1, String param2) {
        Bai_yeu_thich_Fragment fragment = new Bai_yeu_thich_Fragment();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bai_yeu_thich_, container, false);

        rcvUser = mView.findViewById(R.id.rcv_postlike);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        rcvUser.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration= new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(itemDecoration);

        UserAdapter userAdapter = new UserAdapter();
        userAdapter.setData(getListUser());
//        adapter_postquantam=new Adapter_postquantam(getListPost());
//        rcv_data.setAdapter(adapter_postquantam);
        rcvUser.setAdapter(userAdapter);
        return mView;
    }

    private List<post_care> getListUser() {

        List<post_care> list = new ArrayList<>();
        list.add(new post_care(R.drawable.like_1,"[S4L] KẾT QUẢ SOLUTIONS FOR LIFE KỲ 03 TRANSLATING INTO PROTEIN","11/12/2019"));
        list.add(new post_care(R.drawable.like_2,"[THÔNG BÁO] CLB TIN HỌC CAO THẮNG TUYỂN THÀNH VIÊN TÌM HIỂU LEGO MINDSTORM EV3","11/11/2019"));
        list.add(new post_care(R.drawable.like_3 ,"[Problem 03] Translating RNA into Protein","22/11/2019"));
        return list;
    }
}