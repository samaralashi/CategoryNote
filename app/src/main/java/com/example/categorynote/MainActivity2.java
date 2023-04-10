package com.example.categorynote;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements MyAdapter2.ItemClickListener, MyAdapter2.ItemClickListener2 {

    RecyclerView recyclerView;
    ArrayList<Note> noteItemArrayList;
    MyAdapter2 myAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference mNotesRef = db.collection("Note");
    private FirebaseAnalytics mFirebaseAnalytics;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int min = calendar.get(Calendar.MINUTE);
    int sec = calendar.get(Calendar.SECOND);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.rvCategoryItemList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        db = FirebaseFirestore.getInstance();
        noteItemArrayList = new ArrayList<Note>();
        myAdapter = new MyAdapter2(MainActivity2.this, noteItemArrayList, this, this);

        recyclerView.setAdapter(myAdapter);

//        String categoryId = getIntent().getStringExtra("categoryId");

//        mNotesRef = db.collection("Categories").document(categoryId).collection("note");
//        screenTrack("MainActivity2");
        GetAllNotes();



    }

    private void GetAllNotes() {

        Query query = mNotesRef.whereEqualTo("categoryId", getIntent().getStringExtra("idc"));
        Log.e("tag", getIntent().getStringExtra("idc"));
        query.get()


        //db.collection("Note").get()

                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("drn", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String noteId = documentSnapshot.getId();
                                    String noteName = documentSnapshot.getString("noteName");
                                    Note note = new Note(noteId, noteName);
                                    noteItemArrayList.add(note);
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(myAdapter);
                                    myAdapter.notifyDataSetChanged();
                                    Log.e("LogDATA", noteItemArrayList.toString());
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
        Note click = noteItemArrayList.get(position);
        String idNote = click.getNoteId();
        Intent intent = new Intent(this, MainActivity3.class);
        intent.putExtra("id", idNote);
        noteEvent(id, "Note Button", noteItemArrayList.get(position).noteName);
        startActivity(intent);
    }

    public void noteEvent(String id, String name, String content){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
//    public void screenTrack(String screenName){
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);
//        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "Main Activity");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
//    }

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
        users.put("screen Name", "MainActivity2");

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