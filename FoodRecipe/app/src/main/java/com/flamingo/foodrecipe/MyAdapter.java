package com.flamingo.foodrecipe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    private Context mContext;
    private List<FoodInfo> myFoodList;

    public MyAdapter(Context mContext, List<FoodInfo> myFoodList) {
        this.mContext = mContext;
        this.myFoodList = myFoodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent,false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder holder, int position) {

        //showing selected image with the help of glide
        Glide.with(mContext)
                .load(myFoodList.get(position).getItemImage())
                .into(holder.imageView);

        //holder.imageView.setImageResource(Integer.parseInt(String.valueOf(myFoodList.get(position).getItemImage())));
        holder.mTitle.setText(myFoodList.get(position).getItemName());
        holder.mDescription.setText(myFoodList.get(position).getItemDescription());
        holder.mType.setText(myFoodList.get(position).getItemType());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FoodDetailActivity.class);
                intent.putExtra("Image", myFoodList.get(holder.getAdapterPosition()).getItemImage());
                intent.putExtra("Title", myFoodList.get(holder.getAdapterPosition()).getItemName());
                intent.putExtra("Description", myFoodList.get(holder.getAdapterPosition()).getItemDescription());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return myFoodList.size();
    }

    public void filteredList(ArrayList<FoodInfo> filterList) {

        myFoodList = filterList;
        notifyDataSetChanged();
    }
}



class FoodViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView mTitle, mDescription, mType;
    CardView mCardView;

    public FoodViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mDescription = itemView.findViewById(R.id.tvDescription);
        mType = itemView.findViewById(R.id.tvType);

        mCardView = itemView.findViewById(R.id.myCardView);
    }
}
