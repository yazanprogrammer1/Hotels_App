package com.example.hotelbooking.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotelbooking.R;
import com.example.hotelbooking.apis.RetrofitUpdateHotel;
import com.example.hotelbooking.databinding.ActivityUpadteHotelBinding;
import com.example.hotelbooking.model.Hotel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpadteHotelActivity extends AppCompatActivity {

    ActivityUpadteHotelBinding binding;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpadteHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //.... code Update Hotel Admin
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent ii = result.getData();
                            Uri uri = ii.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                                binding.imageProfile.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
        binding.cardCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(i);
            }
        });
        init();
        clickItem();
    }

    private void init() {
        int id = getIntent().getIntExtra("idHotelUp", 0);
        String name = getIntent().getStringExtra("nameHotelUp");
        String rating = getIntent().getStringExtra("rateHotelUp");
        String address = getIntent().getStringExtra("locationHotelUp");
        String phone = getIntent().getStringExtra("phoneHotelUp");
        String image = getIntent().getStringExtra("imageHotelUp");
        binding.nameHotel.getEditText().setText(name);
        binding.rateHotel.getEditText().setText(rating);
        binding.locationHotel.getEditText().setText(address);
        binding.phoneHotel.getEditText().setText(phone);
        Glide.with(getApplicationContext())
                .load("http://10.0.2.2/db/Hotel/" + image)
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.hotel1)
                .into(binding.imageProfile);
    }

    private void clickItem() {
        binding.btnBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHotelById();
            }
        });
    }

    public void updateHotelById() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String base46Image = Base64.encodeToString(bytes, Base64.DEFAULT);
            int id = getIntent().getIntExtra("idHotelUp", 0);
            String name = binding.nameHotel.getEditText().getText().toString();
            String rate = binding.rateHotel.getEditText().getText().toString();
            String location = binding.locationHotel.getEditText().getText().toString();
            String phone = binding.phoneHotel.getEditText().getText().toString();
            Call<Hotel> call = RetrofitUpdateHotel.getInstance().getMyApi().updateHotel(id, name,
                    rate, location, phone, base46Image);
            call.enqueue(new Callback<Hotel>() {
                @Override
                public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Hotel> call, Throwable t) {
                    Log.e("yazan", t.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Select new or same Image", Toast.LENGTH_SHORT).show();
        }
    }

}