package com.example.hotelbooking.ui.Cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.adapter.ReservationUserAdapter;
import com.example.hotelbooking.apis.RetrofitGetReservation;
import com.example.hotelbooking.databinding.FragmentCartBinding;
import com.example.hotelbooking.model.Reservation;
import com.example.hotelbooking.shared_Prefe.Shared_Preference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //... code
        getReservationById();
    }

    public void getReservationById() {
        int id = Shared_Preference.getInstance(requireActivity()).getUsers().getId();
        Call<List<Reservation>> call = RetrofitGetReservation.getInstance().getApi().DispalyReservation(id);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                List<Reservation> productsList = response.body();
                ReservationUserAdapter adapter = new ReservationUserAdapter(productsList, requireActivity());
                adapter.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}