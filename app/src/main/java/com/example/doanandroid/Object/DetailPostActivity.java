package com.example.doanandroid.Object;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanandroid.Adapter.AdapterComment;
import com.example.doanandroid.Adapter.AdapterRecruit_CNTT;
import com.example.doanandroid.Model.Comment;
import com.example.doanandroid.Model.Infor_All_CNTT;
import com.example.doanandroid.Model.Recruit_CNTT;
import com.example.doanandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailPostActivity extends AppCompatActivity {

    private TextView tvTitle, tvContent, tvLike, tvCreate, tvView;
    private Recruit_CNTT recruit_cntt;
    private String postID;
    private FloatingActionButton floatingActionButton;
    private boolean isLike = true;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private RecyclerView recyclerView;
    private EditText edtComment;
    private ImageView imgSent;
    private  ArrayList<Comment> comments = new ArrayList<>();
    private AdapterComment adapterComment;
    private String tylePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        recruit_cntt = (Recruit_CNTT) getIntent().getSerializableExtra("recruit");

        if (recruit_cntt.isRecruit())
        {
            tylePost = "recruit";
        }else{
            tylePost = "list_all_content";

        }
        setUpViews();
        getdata();
        initEvents();

    }

    public void hindeInputKey(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initEvents() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike){
                    increaseLikeInPostDeail();
                    saveLikeInLikesDB();
                }else{
                    reduceLikeInPostDetail();
                    deleteLikeInLikesDB();
                }

            }
        });

        imgSent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtComment.getText()) && edtComment.getText().toString().length() > 5){
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_comment");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    Comment comment = new Comment();
                    comment.setContent(edtComment.getText().toString());
                    comment.setDate(dateFormat.format(date));
                    comment.setNameUser(user.getDisplayName());
                    mDatabase.child(postID).push().setValue(comment, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(getApplicationContext(),"Successfully!", Toast.LENGTH_SHORT).show();
                            edtComment.setText("");
                            hindeInputKey();
                        }
                    });
                    getComments();
                }
            }
        });

    }

    private void getComments(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_comment");
        mDatabase.child(postID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Comment comment = dt.getValue(Comment.class);
                    comments.add(comment);
                }
                 adapterComment = new AdapterComment(comments);
                recyclerView.setAdapter(adapterComment);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("asd", databaseError.toString());
            }
        });
    }

    private void deleteLikeInLikesDB() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("List_like");
        mDatabase.child(user.getUid()).child(postID).removeValue();
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.heart));
        isLike = true;
    }

    private void reduceLikeInPostDetail() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        mDatabase.child(tylePost).child(postID).child("like").setValue((recruit_cntt.getLike() - 1));
    }

    void checkLike(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("List_like");
        mDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String id = childSnapshot.getKey().toString();
                    if (id.equals(postID)){
                        isLike = false;
                        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.brokenheart));
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("asd", databaseError.toString());
            }
        });
    }

    private void saveLikeInLikesDB() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("List_like");
        mDatabase.child(user.getUid()).child(postID).setValue("");

    }

    private void increaseLikeInPostDeail() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        mDatabase.child(tylePost).child(postID).child("like").setValue((recruit_cntt.getLike() +1));
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.brokenheart));
        isLike = false;
    }

    private void getdata(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        mDatabase.child(tylePost).orderByChild("id").equalTo(recruit_cntt.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    postID = childSnapshot.getKey().toString();
                    getDataByPostID(postID);
                    increaseView();
                    checkLike();
                    getComments();
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
        mDatabase.child(tylePost).child(postID).child("view").setValue((recruit_cntt.getView() +1));
    }

    void getDataByPostID(String PostId){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("list_cntt");
        // get data từ firebase
        mDatabase.child(tylePost).child(PostId).addValueEventListener(new ValueEventListener() {
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
        tvView.setText("View: "+recruit_cntt.getView()+"");
        tvCreate.setText("Date: "+recruit_cntt.getDate());
        tvLike.setText("Like: "+ recruit_cntt.getLike()+"");
        tvContent.setText(Html.fromHtml(recruit_cntt.getContent()));

    }

    private void setUpViews() {
        tvTitle = findViewById(R.id.detail_post_title);
        tvContent = findViewById(R.id.detail_post_view);
        tvView = findViewById(R.id.detail_post);
        tvLike = findViewById(R.id.detail_post_like);
        tvCreate = findViewById(R.id.detail_post_date_create);
        floatingActionButton = findViewById(R.id.fab_detail_post);
        recyclerView = findViewById(R.id.rcv_detail_post);
        edtComment = findViewById(R.id.edt_detail_post_messeger);
        imgSent = findViewById(R.id.img_sent);
        //recyclerView = new AdapterComment(recruit_cnttList);
        adapterComment = new AdapterComment(comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterComment);
    }
}

