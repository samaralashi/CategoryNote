package com.example.categorynote;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    Context context;
    List<Note> noteItemArrayList;
    private MyAdapter2.ItemClickListener mClickListener;
    private MyAdapter2.ItemClickListener2 itemClickListener2;

    public MyAdapter2(Context context, List<Note> noteItemArrayList, ItemClickListener onClick, ItemClickListener2 onClick2) {
        this.context = context;
        this.noteItemArrayList = noteItemArrayList;
        this.mClickListener = onClick;
        this.itemClickListener2 = onClick2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyAdapter2.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note noteItem = noteItemArrayList.get(position);
        holder.tvNoteName.setText(noteItem.noteName);

    }

    @Override
    public int getItemCount() {
        return noteItemArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNoteName;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvNoteName = itemView.findViewById(R.id.tvNoteName);
            this.cardView = itemView.findViewById(R.id.card2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }


    public interface ItemClickListener {
        void onItemClick(int position, String id);

    }

    public interface ItemClickListener2 {
        void onItemClick2(int position, String id);
    }
}

