package com.example.hotelbooking.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.HotelsUserAdapter;
import com.example.hotelbooking.apis.RetrofitGetHotels;
import com.example.hotelbooking.apis.RetrofitGetImageProfile;
import com.example.hotelbooking.databinding.FragmentHomeBinding;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.Result;
import com.example.hotelbooking.shared_Prefe.Shared_Preference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeaderInfo();
        binding.shimmer.startShimmer();
        getHotels();

    }

    public void setHeaderInfo() {
        String name = Shared_Preference.getInstance(requireActivity()).getUsers().getName();
        String email = Shared_Preference.getInstance(requireActivity()).getUsers().getEmail();
        String phone = Shared_Preference.getInstance(requireActivity()).getUsers().getPhone();
        int id = Shared_Preference.getInstance(requireActivity()).getUsers().getId();
        getImageUser(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getHotels() {
        Call<List<Hotel>> call = RetrofitGetHotels.getInstance().getApi().DisplayHotel();
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                List<Hotel> productsList = response.body();
                HotelsUserAdapter adapter = new HotelsUserAdapter(productsList, getContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
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
                Toast.makeText(getContext(), "error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("yazan", t.getMessage());
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.listPopularHotels.setVisibility(View.VISIBLE);
            }
        });

    }


    public void getImageUser(int user_id) {
        Call<Result> call = RetrofitGetImageProfile.getInstance().getMyApi().getImageProfile(user_id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Glide.with(requireActivity())
                        .load("http://10.0.2.2/db/Hotel/" + response.body().getImagesUser())
                        .apply(new RequestOptions().override(600, 600))
                        .error(R.drawable.hotel1)
                        .into(binding.imageUser);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }


}