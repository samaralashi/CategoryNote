package com.example.categorynote;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener, MyAdapter.ItemClickListener2 {

    RecyclerView recyclerView;
    RecyclerView categoryList;
    ArrayList<Category> categoryArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    private long startTime;
    private FirebaseAnalytics mFirebaseAnalytics;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//    private MyAdapter.ItemClickListener mClickListener;
//    private MyAdapter.ItemClickListener2 itemClickListener2;

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int min = calendar.get(Calendar.MINUTE);
    int sec = calendar.get(Calendar.SECOND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        recyclerView = findViewById(R.id.rvCategoryNoteList);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//
//        db = FirebaseFirestore.getInstance();
//        categoryArrayList = new ArrayList<Category>();
//        myAdapter = new MyAdapter(this, categoryArrayList, this,  this);
//
//        recyclerView.setAdapter(myAdapter);
//        GetAllNotes();
//        screenTrack("MainActivity");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        db = FirebaseFirestore.getInstance();
        startTime = System.currentTimeMillis();

        categoryList = findViewById(R.id.rvCategoryNoteList);
        categoryList.setLayoutManager(new LinearLayoutManager(this));

        recyclerView = findViewById(R.id.rvCategoryNoteList);
        categoryArrayList = new ArrayList<Category>();
        myAdapter = new MyAdapter(this, categoryArrayList, this, this);
        GetAllNotes();
        screenTrack("MainActivity");


    }

    private void GetAllNotes() {

        db.collection("Categories").get()

                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("drn", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String categoryName = documentSnapshot.getString("CategoryName");
                                    Category category= new Category(id, categoryName);
                                    categoryArrayList.add(category);
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(myAdapter);
                                    myAdapter.notifyDataSetChanged();
                                    Log.e("LogDATA", categoryArrayList.toString());
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LogDATA", "get failed with ");


                    }
                });
    }

    @Override
    public void onItemClick(int position, String id) {

    }

    @Override
    public void onItemClick2(int position, String id) {
        Category clickedItem = categoryArrayList.get(position);
        String idc = clickedItem.getId();
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("id", idc);
        Log.e("samar", idc);
        categoryEvent(idc, "Category Button", categoryArrayList.get(position).categoryName);
        startActivity(intent);

    }

    public void categoryEvent(String id, String name, String content){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
    public void screenTrack(String screenName){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "Main Activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    @Override
    protected void onPause() {
        Calendar calendar = Calendar.getInstance();
        int hour2 = calendar.get(Calendar.HOUR);
        int min2 = calendar.get(Calendar.MINUTE);
        int sec2 = calendar.get(Calendar.SECOND);

        int h = hour2-hour;
        int m = min2-min;
        int s = sec2-sec;

        HashMap<String, Object> users = new HashMap<>();
        users.put("name", "samar");
        users.put("hours", h);
        users.put("minutes", m);
        users.put("seconds", s);
        users.put("screen Name", "MainActivity");


        db.collection("users")
                .add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        super.onPause();
    }
}