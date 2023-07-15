package com.example.hotelbooking.Activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.adapter.ReservationAdminAdapter;
import com.example.hotelbooking.apis.RetrofitGetAllReservationForAdmin;
import com.example.hotelbooking.databinding.ActivityViewAllReservationAdminBinding;
import com.example.hotelbooking.model.Reservation;
import com.example.hotelbooking.shared_Prefe.Shared_Preference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllReservation_AdminActivity extends AppCompatActivity {

    ActivityViewAllReservationAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllReservationAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //... code Reservation
        getAllReservations();
    }

    public void getAllReservations() {
        int id = Shared_Preference.getInstance(getApplicationContext()).getUsers().getId();
        Call<List<Reservation>> call = RetrofitGetAllReservationForAdmin.getInstance().getApi().DispalyReservationAdmin();
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                List<Reservation> productsList = response.body();
                ReservationAdminAdapter adapter = new ReservationAdminAdapter(productsList, getApplicationContext());
                adapter.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                binding.listReservation.setLayoutManager(layoutManager);
                binding.listReservation.setHasFixedSize(true);
                binding.listReservation.setAdapter(adapter);
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.listReservation.setVisibility(View.VISIBLE);
                Log.e("yazan", "Created :)");
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.e("yazan", t.getMessage());
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.listReservation.setVisibility(View.VISIBLE);
            }
        });

    }

}