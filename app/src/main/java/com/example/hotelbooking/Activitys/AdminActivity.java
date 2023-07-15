package com.example.hotelbooking.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.adapter.HotelsAdapter;
import com.example.hotelbooking.apis.RetrofitGetHotels;
import com.example.hotelbooking.databinding.ActivityAdminBinding;
import com.example.hotelbooking.model.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //... code Admin Panel
        builder = new AlertDialog.Builder(this);
        clickItem();
        getHotels();
    }

    private void clickItem() {
        binding.btnAddHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddHotelActivity.class));
            }
        });
        binding.btnReservationHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewAllReservation_AdminActivity.class));
            }
        });
    }


    public void getHotels() {
        Call<List<Hotel>> call = RetrofitGetHotels.getInstance().getApi().DisplayHotel();
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                List<Hotel> productsList = response.body();
                HotelsAdapter adapter = new HotelsAdapter(productsList, getApplicationContext());
                adapter.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                binding.listPopularHotels.setLayoutManager(layoutManager);
                binding.listPopularHotels.setHasFixedSize(true);
                binding.listPopularHotels.setAdapter(adapter);
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.listPopularHotels.setVisibility(View.VISIBLE);
                Log.e("yazan", "Created :)");
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Log.e("yazan", t.getMessage());
                binding.errorImage.setVisibility(View.VISIBLE);
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.listPopularHotels.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Toast.makeText(getApplicationContext(), "you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();
    }
}