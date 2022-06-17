package com.example.doanandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailPostActivity extends AppCompatActivity {

    TextView tvTitle, tvContent, tvLike, tvCreate, tvView;
    Recruit_CNTT recruit_cntt;
    String postID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        recruit_cntt = (Recruit_CNTT) getIntent().getSerializableExtra("recruit");
        setUpViews();
        getdata();


    }

    private void getdata(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        mDatabase.child("recruit").orderByChild("id").equalTo(recruit_cntt.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    postID = childSnapshot.getKey().toString();
                    getDataByPostID(postID);
                    increaseView();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("asd", databaseError.toString());
            }
        });
    }
    private void increaseView() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        // get data từ firebase
        mDatabase.child("recruit").child(postID).child("view").setValue((recruit_cntt.getView() +1));
    }

    void getDataByPostID(String PostId){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        // get data từ firebase
        mDatabase.child("recruit").child(PostId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    recruit_cntt = snapshot.getValue(Recruit_CNTT.class);
                    setDataForViews(recruit_cntt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDataForViews(Recruit_CNTT recruit_cntt) {
        tvTitle.setText(recruit_cntt.getTitle());
        tvView.setText("view: "+recruit_cntt.getView()+"");
        tvCreate.setText("date: "+recruit_cntt.getDate());
        tvLike.setText("Like: "+ recruit_cntt.getLike()+"");
        tvContent.setText(Html.fromHtml(recruit_cntt.getContent()));

    }

    private void setUpViews() {
        tvTitle = findViewById(R.id.detail_post_title);
        tvContent = findViewById(R.id.detail_post_view);
        tvView = findViewById(R.id.detail_post);
        tvLike = findViewById(R.id.detail_post_like);
        tvCreate = findViewById(R.id.detail_post_date_create);

    }
}