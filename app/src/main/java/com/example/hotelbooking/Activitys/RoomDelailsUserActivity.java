package com.example.hotelbooking.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.ActivityRoomDelailsUserBinding;
import com.example.hotelbooking.shared_Prefe.Shared_Preference;

public class RoomDelailsUserActivity extends AppCompatActivity {

    ActivityRoomDelailsUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomDelailsUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //..
        setHeaderInfo();
        clickItem();
    }

    public void setHeaderInfo() {
        int idRoom = getIntent().getIntExtra("idRoom", 0);
        String numberRoom = getIntent().getStringExtra("numberRoom");
        String ratingRoom = getIntent().getStringExtra("ratingRoom");
        String numDay = getIntent().getStringExtra("numDay");
        String descRoom = getIntent().getStringExtra("descrptionRoom");
        String priceRoom = getIntent().getStringExtra("PriceRoom");
        String image = getIntent().getStringExtra("imageRoom");
        binding.nameRoomDet.setText(numberRoom);
        binding.rateRoomDe.setRating(Float.parseFloat(ratingRoom));
        binding.priceRoomDet.setText(priceRoom + "$" + " / " + numDay + " night");
        binding.tvDesc.setText(descRoom);
        Glide.with(getApplicationContext())
                .load("http://10.0.2.2/db/Hotel/" + image)
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.hotel1)
                .into(binding.imgRoomDet);
    }

    private void clickItem() {
        binding.ReserveTheRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_room = getIntent().getIntExtra("idRoom", 0);
                Intent i = new Intent(getApplicationContext(), InsertReservationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id_user", Shared_Preference.getInstance(getApplicationContext()).getUsers().getId());
                i.putExtra("id_room", id_room);
                startActivity(i);
            }
        });
    }


}