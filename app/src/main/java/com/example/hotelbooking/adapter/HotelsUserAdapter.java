package com.example.hotelbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotelbooking.Activitys.ViewRoomUserActivity;
import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Hotel;

import java.util.List;

public class HotelsUserAdapter extends RecyclerView.Adapter<HotelsUserAdapter.myViewHolder> {

    private List<Hotel> productsList;
    private Context context;

    public HotelsUserAdapter(List<Hotel> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.name.setText(productsList.get(position).getName());
        holder.rate.setRating(Float.parseFloat(productsList.get(position).getRating()));
        holder.location.setText(productsList.get(position).getAddress());
        Glide.with(context).load("http://10.0.2.2/db/Hotel/" + productsList.get(position).getImage())
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.ic_launcher_background)
                .into(holder.img_capital);

        holder.cardView_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ViewRoomUserActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", productsList.get(position).getId());
                i.putExtra("name", productsList.get(position).getName());
                i.putExtra("rating", productsList.get(position).getRating());
                i.putExtra("address", productsList.get(position).getAddress());
                i.putExtra("phone", productsList.get(position).getPhone());
                i.putExtra("image", productsList.get(position).getImage());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView name, location;
        ImageView img_capital;
        CardView cardView_cart;
        RatingBar rate;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_Hotel);
            rate = itemView.findViewById(R.id.rate_Hotel);
            location = itemView.findViewById(R.id.location_Hotel);
            img_capital = itemView.findViewById(R.id.image_Hotel);
            cardView_cart = itemView.findViewById(R.id.card_ViewRoom);
        }
    }
}
