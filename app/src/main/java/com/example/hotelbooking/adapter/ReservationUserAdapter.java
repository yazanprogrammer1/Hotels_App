package com.example.hotelbooking.adapter;

import android.content.Context;
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
import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Reservation;

import java.util.List;

public class ReservationUserAdapter extends RecyclerView.Adapter<ReservationUserAdapter.myViewHolder> {

    private List<Reservation> productsList;
    private Context context;

    public ReservationUserAdapter(List<Reservation> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item_reservation, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.numberRoom.setText("number Room : " + productsList.get(position).getNumber());
        holder.count_person.setText("count person : " + productsList.get(position).getCount_person());
        holder.rate.setRating(Float.parseFloat(productsList.get(position).getRating()));
        holder.price.setText(productsList.get(position).getPrice()
                + "$" + " / " + productsList.get(position).getCount_day() + " night");
        holder.nameHotel.setText("name Hotel : " + productsList.get(position).getNameHotel());
        holder.check_in.setText("check in : " + productsList.get(position).getCheck_in());
        holder.check_out.setText("check out : " + productsList.get(position).getCheck_out());
        holder.isReservation.setText(productsList.get(position).getIsReservation());
        Glide.with(context).load("http://10.0.2.2/db/Hotel/" + productsList.get(position).getImage())
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.ic_launcher_background)
                .into(holder.img_capital);

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView numberRoom, count_person, price, nameHotel, check_in, check_out, isReservation;
        ImageView img_capital;
        CardView card_deleteRoom;
        RatingBar rate;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            numberRoom = itemView.findViewById(R.id.tv_numberRoom_reservation);
            count_person = itemView.findViewById(R.id.tvCount_person);
            rate = itemView.findViewById(R.id.rate_Room_reservation);
            price = itemView.findViewById(R.id.tv_priceRoom_reservation);
            nameHotel = itemView.findViewById(R.id.tv_nameHotel_reservation);
            check_in = itemView.findViewById(R.id.tv_check_in_reservation);
            check_out = itemView.findViewById(R.id.tv_check_out_reservation);
            card_deleteRoom = itemView.findViewById(R.id.card_deleteRoom);
            img_capital = itemView.findViewById(R.id.image_room_reservation);
            isReservation = itemView.findViewById(R.id.btn_isReservation);
        }
    }
}
