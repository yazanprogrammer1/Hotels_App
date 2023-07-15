package com.example.hotelbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotelbooking.R;
import com.example.hotelbooking.apis.RetrofitUpdateReservationById;
import com.example.hotelbooking.model.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationAdminAdapter extends RecyclerView.Adapter<ReservationAdminAdapter.myViewHolder> {

    private List<Reservation> productsList;
    private Context context;

    public ReservationAdminAdapter(List<Reservation> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item_reservation_admin, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.numberRoom.setText("number Room : " + productsList.get(position).getNumber());
        holder.count_person.setText("count person : " + productsList.get(position).getCount_person());
        holder.rate.setRating(Float.parseFloat(productsList.get(position).getRating()));
        holder.price.setText(productsList.get(position).getPrice() + "$" + " / " + productsList.get(position).getCount_day() + " night");
        holder.nameHotel.setText("name Hotel : " + productsList.get(position).getNameHotel());
        holder.check_in.setText("check in : " + productsList.get(position).getCheck_in());
        holder.check_out.setText("check out : " + productsList.get(position).getCheck_out());
        holder.name_user.setText("name : " + productsList.get(position).getName_user());
        holder.phone_user.setText("phone : +970" + productsList.get(position).getPhone_user());

        Glide.with(context).load("http://10.0.2.2/db/Hotel/" + productsList.get(position).getImage()).apply(new RequestOptions().override(600, 600)).error(R.drawable.ic_launcher_background).into(holder.img_capital);
        Glide.with(context).load("http://10.0.2.2/db/Hotel/" + productsList.get(position).getImage_user()).apply(new RequestOptions().override(600, 600)).error(R.drawable.ic_launcher_background).into(holder.image_user);

        holder.cardCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = productsList.get(position).getPhone_user();
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+970" + phone));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productsList.get(position).getId() == 0) {
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                } else {
                    int id = productsList.get(position).getId();
                    Call<Reservation> call = RetrofitUpdateReservationById.getInstance().getMyApi().updateReservationAdmin(id);
                    call.enqueue(new Callback<Reservation>() {
                        @Override
                        public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                            if (response.body().isError_reservation() == false) {
                                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Reservation> call, Throwable t) {
                            Log.e("yazan", t.getMessage());
                        }
                    });
                }
            }
        });
        holder.card_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView numberRoom, count_person, price, nameHotel, check_in, check_out, isReservation, name_user, phone_user;
        ImageView img_capital, image_user;
        CardView card_delete, cardCall;
        RatingBar rate;
        Button buttonOk;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardCall = itemView.findViewById(R.id.card_Call_Reservation);
            numberRoom = itemView.findViewById(R.id.tv_numberRoom_reservation);
            count_person = itemView.findViewById(R.id.tvCount_person);
            rate = itemView.findViewById(R.id.rate_Room_reservation);
            price = itemView.findViewById(R.id.tv_priceRoom_reservation);
            nameHotel = itemView.findViewById(R.id.tv_nameHotel_reservation);
            check_in = itemView.findViewById(R.id.tv_check_in_reservation);
            check_out = itemView.findViewById(R.id.tv_check_out_reservation);
            card_delete = itemView.findViewById(R.id.card_notOk_Reservation);
            img_capital = itemView.findViewById(R.id.image_room_reservation);
            name_user = itemView.findViewById(R.id.nameuser_Reservation);
            phone_user = itemView.findViewById(R.id.phoneuser_Reservation);
            image_user = itemView.findViewById(R.id.image_User_Reservation);
            buttonOk = itemView.findViewById(R.id.btn_Ok_Reservation);
        }
    }
}
