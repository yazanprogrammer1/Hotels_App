package com.example.hotelbooking.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotelbooking.R;
import com.example.hotelbooking.apis.RetrofitDeleteRoom;
import com.example.hotelbooking.databinding.ActivityRoomDelailsBinding;
import com.example.hotelbooking.model.Room;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDelailsActivity extends AppCompatActivity {

    ActivityRoomDelailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomDelailsBinding.inflate(getLayoutInflater());
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
        binding.btnDeleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoomById();
            }
        });

        binding.btnUpdateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRoom = getIntent().getIntExtra("idRoom", 0);
                String numberRoom = getIntent().getStringExtra("numberRoom");
                String ratingRoom = getIntent().getStringExtra("ratingRoom");
                String numDay = getIntent().getStringExtra("numDay");
                String descRoom = getIntent().getStringExtra("descrptionRoom");
                String priceRoom = getIntent().getStringExtra("PriceRoom");
                String imageRoom = getIntent().getStringExtra("imageRoom");
                Intent i = new Intent(getApplicationContext(), UpadteRoomActivity.class);
                i.putExtra("idRoomUp", idRoom);
                i.putExtra("numberRoomUp", numberRoom);
                i.putExtra("rateRoomUp", ratingRoom);
                i.putExtra("numDayRoomUp", numDay);
                i.putExtra("descRoomUp", descRoom);
                i.putExtra("phoneRoomUp", priceRoom);
                i.putExtra("imageRoomUp", imageRoom);
                startActivity(i);
            }
        });
    }

    public void deleteRoomById() {
        int idRoom = getIntent().getIntExtra("idRoom", 0);
        Call<Room> call = RetrofitDeleteRoom.getInstance().getMyApi().deleteRoom(idRoom);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.body().isError_room() == false) {
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    finish();
                }
                Log.e("yazan", "Created :)");
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Log.e("yazan", t.getMessage());
            }
        });
    }


}