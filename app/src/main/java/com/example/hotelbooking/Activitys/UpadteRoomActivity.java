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
import com.example.hotelbooking.apis.RetrofitUpdateRoom;
import com.example.hotelbooking.databinding.ActivityUpadteRoomBinding;
import com.example.hotelbooking.model.Room;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpadteRoomActivity extends AppCompatActivity {

    ActivityUpadteRoomBinding binding;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpadteRoomBinding.inflate(getLayoutInflater());
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
                                binding.imageRoom.setImageBitmap(bitmap);
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
        int idRoom = getIntent().getIntExtra("idRoomUp", 0);
        String numberRoom = getIntent().getStringExtra("numberRoomUp");
        String ratingRoom = getIntent().getStringExtra("rateRoomUp");
        String numDay = getIntent().getStringExtra("numDayRoomUp");
        String descRoom = getIntent().getStringExtra("descRoomUp");
        String priceRoom = getIntent().getStringExtra("phoneRoomUp");
        String imageRoom = getIntent().getStringExtra("imageRoomUp");
        binding.numberRoom.getEditText().setText(numberRoom);
        binding.rateRoom.getEditText().setText(ratingRoom);
        binding.priceRoom.getEditText().setText(priceRoom);
        binding.descrptionRoom.getEditText().setText(descRoom);
        binding.numberDayRoom.getEditText().setText(numDay);
        Glide.with(getApplicationContext())
                .load("http://10.0.2.2/db/Hotel/" + imageRoom)
                .apply(new RequestOptions().override(600, 600))
                .error(R.drawable.hotel1)
                .into(binding.imageRoom);
    }

    private void clickItem() {
        binding.btnBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
        binding.btnUpdateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRoomById();
            }
        });
    }

    public void updateRoomById() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String base46Image = Base64.encodeToString(bytes, Base64.DEFAULT);
            int idRoom = getIntent().getIntExtra("idRoomUp", 0);
            String numRoom = binding.numberRoom.getEditText().getText().toString();
            String rateRoom = binding.rateRoom.getEditText().getText().toString();
            String priceRoom = binding.priceRoom.getEditText().getText().toString();
            String descRoom = binding.descrptionRoom.getEditText().getText().toString();
            String numberDay = binding.numberDayRoom.getEditText().getText().toString();
            Call<Room> call = RetrofitUpdateRoom.getInstance().getMyApi().updateRoom(idRoom, numRoom, numberDay, priceRoom, descRoom, rateRoom, base46Image);
            call.enqueue(new Callback<Room>() {
                @Override
                public void onResponse(Call<Room> call, Response<Room> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Room> call, Throwable t) {
                    Log.e("yazan", t.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Select new or same Image", Toast.LENGTH_SHORT).show();
        }
    }

}