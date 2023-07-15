package com.example.hotelbooking.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.RoomAdapter;
import com.example.hotelbooking.apis.RetrofitDeleteHotel;
import com.example.hotelbooking.apis.RetrofitGetRoom;
import com.example.hotelbooking.databinding.ActivityViewRoomAdminBinding;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRoomAdminActivity extends AppCompatActivity {

    ActivityViewRoomAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewRoomAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //... code delete and update and addRoom and viewRoom
        setHeaderInfo();
        clickItem();
        getRoomById();
    }

    public void setHeaderInfo() {
        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        String rating = getIntent().getStringExtra("rating");
        String address = getIntent().getStringExtra("address");
        String phone = getIntent().getStringExtra("phone");
        String image = getIntent().getStringExtra("image");
        binding.textView2.setText(name);
        binding.rateHotelDe.setRating(Float.parseFloat(rating));
        binding.addressHotel.setText(address);
        Glide.with(getApplicationContext())
                .load("http://10.0.2.2/db/Hotel/" + image)
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.hotel1)
                .into(binding.imgHotel);
    }

    private void clickItem() {
        binding.btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getIntExtra("id", 0);
                Intent i = new Intent(getApplicationContext(), AddRoomActivity.class);
                i.putExtra("idHotel", id);
                startActivity(i);
            }
        });
        binding.btnDeleteHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHotelById();
            }
        });
        binding.btnUpdateHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getIntExtra("id", 0);
                String name = getIntent().getStringExtra("name");
                String rating = getIntent().getStringExtra("rating");
                String address = getIntent().getStringExtra("address");
                String phone = getIntent().getStringExtra("phone");
                String image = getIntent().getStringExtra("image");
                Intent i = new Intent(getApplicationContext(), UpadteHotelActivity.class);
                i.putExtra("idHotelUp", id);
                i.putExtra("nameHotelUp", name);
                i.putExtra("rateHotelUp", rating);
                i.putExtra("locationHotelUp", address);
                i.putExtra("phoneHotelUp", phone);
                i.putExtra("imageHotelUp", image);
                startActivity(i);
            }
        });
        binding.cardCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = getIntent().getStringExtra("phone");
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+970" + phone));
                startActivity(i);
            }
        });
    }

    public void getRoomById() {
        int id = getIntent().getIntExtra("id", 0);
        Call<List<Room>> call = RetrofitGetRoom.getInstance().getApi().DisplayRoom(id);
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                List<Room> productsList = response.body();
                RoomAdapter adapter = new RoomAdapter(productsList, getApplicationContext());
                adapter.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                binding.listPopularRoom.setLayoutManager(layoutManager);
                binding.listPopularRoom.setHasFixedSize(true);
                binding.listPopularRoom.setAdapter(adapter);
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.listPopularRoom.setVisibility(View.VISIBLE);
                Log.e("yazan", "Created :)");
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e("yazan", t.getMessage());
                binding.errorImage.setVisibility(View.VISIBLE);
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.listPopularRoom.setVisibility(View.VISIBLE);
            }
        });

    }

    public void deleteHotelById() {
        int id = getIntent().getIntExtra("id", 0);
        Call<Hotel> call = RetrofitDeleteHotel.getInstance().getMyApi().deleteHotel(id);
        call.enqueue(new Callback<Hotel>() {
            @Override
            public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                if (response.body().getError_hotel() == false) {
                    Toast.makeText(ViewRoomAdminActivity.this, "Delete Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    finish();
                }
                Log.e("yazan", "Created :)");
            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {
                Log.e("yazan", t.getMessage());
            }
        });
    }


}//End deleteHotel method

