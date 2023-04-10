package com.example.categorynote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Category> categoryArrayList;
    private ItemClickListener mClickListener;
    private ItemClickListener2 itemClickListener2;


    public MyAdapter(Context context, List<Category> categoryArrayList, ItemClickListener onClick, ItemClickListener2 onClick2) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.mClickListener = onClick;
        this.itemClickListener2 = onClick2;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_items, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Category category = categoryArrayList.get(position);
//        holder.tvCategoryName.setText(category.categoryName);
        holder.tvCategoryName.setText(categoryArrayList.get(position).getCategoryName());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener2.onItemClick2(holder.getAdapterPosition(), categoryArrayList.get(position).categoryId);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategoryName;
        public CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvCategoryName = itemView.findViewById(R.id.tvCategoryItemName);
            this.card = itemView.findViewById(R.id.card);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Category category = categoryArrayList.get(position);
                    String id = category.getId();
                }
            });
        }


    }
    public interface ItemClickListener {
        void onItemClick(int position, String id);

    }

    public interface ItemClickListener2 {
        void onItemClick2(int position, String id);
    }
}
