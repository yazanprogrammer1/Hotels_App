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
import com.example.hotelbooking.Activitys.RoomDelailsActivity;
import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.myViewHolder> {

    private List<Room> RoomList;
    private Context context;

    public RoomAdapter(List<Room> RoomList, Context context) {
        this.RoomList = RoomList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item_room, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.number.setText(RoomList.get(position).getNumber());
        holder.rate.setRating(Float.parseFloat(RoomList.get(position).getRating()));
        holder.price.setText(RoomList.get(position).getPrice()
                + "$" + " / " + RoomList.get(position).getCount_day() + " night");
        Glide.with(context).load("http://10.0.2.2/db/Hotel/" + RoomList.get(position).getImage())
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.ic_launcher_background)
                .into(holder.img_capital);

        holder.cardView_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent.FLAG_ACTIVITY_MULTIPLE_TASK    تفتح اكثر من واجهة للتطبيق
                Intent i = new Intent(context, RoomDelailsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("idRoom", RoomList.get(position).getId());
                i.putExtra("numberRoom", RoomList.get(position).getNumber());
                i.putExtra("ratingRoom", RoomList.get(position).getRating());
                i.putExtra("descrptionRoom", RoomList.get(position).getDescrption());
                i.putExtra("PriceRoom", RoomList.get(position).getPrice());
                i.putExtra("imageRoom", RoomList.get(position).getImage());
                i.putExtra("numDay", RoomList.get(position).getCount_day());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return RoomList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView number, price;
        ImageView img_capital;
        CardView cardView_cart;
        RatingBar rate;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.Number);
            rate = itemView.findViewById(R.id.rate);
            price = itemView.findViewById(R.id.price);
            img_capital = itemView.findViewById(R.id.image);
            cardView_cart = itemView.findViewById(R.id.card_delRoom);
        }
    }
}
