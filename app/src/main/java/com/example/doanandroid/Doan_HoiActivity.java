package com.example.doanandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.doanandroid.Model.Comment;
import com.example.doanandroid.Model.New_Tranning;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Doan_HoiActivity extends AppCompatActivity {
    private TextView tvTitle, tvContent, tvLike, tvCreate, tvView;
    private New_Tranning new_tranning;
    private String postID;
    private FloatingActionButton floatingActionButton;
    private boolean isLike = true;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private RecyclerView recyclerView;
    private EditText edtComment;
    private ImageView imgSent;
    private ArrayList<Comment> comments = new ArrayList<>();
    private AdapterComment adapterComment;
    private String tylePost;
    private final String RootType = "list_group";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doan_hoi);
        new_tranning = (New_Tranning) getIntent().getSerializableExtra("recruit");

        if (new_tranning.getTypePost() == 0)
        {
            tylePost = "list_group_youth";
        }else if (new_tranning.getTypePost() == 1){
            tylePost = "list_new_update";
        }else{
            tylePost = "list_group_student";
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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(RootType);
        mDatabase.child(tylePost).child(postID).child("like").setValue((new_tranning.getLike() - 1));
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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(RootType);
        mDatabase.child(tylePost).child(postID).child("like").setValue((new_tranning.getLike() +1));
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.brokenheart));
        isLike = false;
    }

    private void getdata(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(RootType);
        mDatabase.child(tylePost).orderByChild("id").equalTo(new_tranning.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(RootType);
        // get data từ firebase
        mDatabase.child(tylePost).child(postID).child("view").setValue((new_tranning.getView() +1));
    }

    void getDataByPostID(String PostId){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(RootType);
        // get data từ firebase
        mDatabase.child(tylePost).child(PostId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new_tranning = snapshot.getValue(New_Tranning.class);
                setDataForViews(new_tranning);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDataForViews(New_Tranning new_tranning) {
        tvTitle.setText(new_tranning.getTitle());
        tvView.setText("View: "+new_tranning.getView()+"");
        tvCreate.setText("Date: "+new_tranning.getDate());
        tvLike.setText("Like: "+ new_tranning.getLike()+"");
        tvContent.setText(Html.fromHtml(new_tranning.getContent_link()));

    }

    private void setUpViews() {
        tvTitle = findViewById(R.id.detail_post_doanhoi_title);
        tvContent = findViewById(R.id.detail_post_doanhoi_view);
        tvView = findViewById(R.id.detail_post_doanhoi);
        tvLike = findViewById(R.id.detail_post_doanhoi_like);
        tvCreate = findViewById(R.id.detail_post_doanhoi_date_create);
        floatingActionButton = findViewById(R.id.fab_detail_post_doanhoi);
        recyclerView = findViewById(R.id.rcv_detail_post_doanhoi);
        edtComment = findViewById(R.id.edt_detail_post_doanhoi_messeger);
        imgSent = findViewById(R.id.img_sent_doanhoi);
        //recyclerView = new AdapterComment(recruit_cnttList);
        adapterComment = new AdapterComment(comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterComment);
    }
}