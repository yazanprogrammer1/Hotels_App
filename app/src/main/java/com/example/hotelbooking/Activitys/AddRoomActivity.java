package com.example.hotelbooking.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.apis.RetrofitAddRoom;
import com.example.hotelbooking.databinding.ActivityAddRoomBinding;
import com.example.hotelbooking.model.Room;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRoomActivity extends AppCompatActivity {

    ActivityAddRoomBinding binding;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //... code Add hotel Admin
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
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
        clickItem();
    }

    private void clickItem() {
        binding.btnBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
            }
        });
        binding.btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numberRoomValidate() || !rateRoomValidate() || !descriptionRoomValidate() || !numDayRoomValidate() || !priceRoomValidate()) {
                    return;
                } else {
                    insertHotel();
                }
            }
        });
    }

    private void insertHotel() {
        //retrieve data from Edit texts
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String base46Image = Base64.encodeToString(bytes, Base64.DEFAULT);
            String numberRoom = binding.numberRoom.getEditText().getText().toString().trim();
            String rateRoom = binding.rateRoom.getEditText().getText().toString().trim();
            String descrptionRoom = binding.descrptionRoom.getEditText().getText().toString().trim();
            String priceRoom = binding.priceRoom.getEditText().getText().toString().trim();
            String numberDayRoom = binding.numberDayRoom.getEditText().getText().toString().trim();
            int hotelId = getIntent().getIntExtra("idHotel", 0);

            //Here we will handle the http request to insert user to mysql db
            Call<Room> call = RetrofitAddRoom.getInstance().getMyApi().insertRoom(numberRoom, numberDayRoom, priceRoom, descrptionRoom, rateRoom, hotelId, base46Image);
            call.enqueue(new Callback<Room>() {
                @Override
                public void onResponse(Call<Room> call, Response<Room> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Room> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
        }
    }//End insertHotel method

    public boolean numberRoomValidate() {
        String usernameStr = binding.numberRoom.getEditText().getText().toString().trim();
        if (usernameStr.isEmpty()) {
            binding.numberRoom.setError("numberRoom cannot be empty");
            return false;
        } else if (usernameStr.length() > 20) {
            binding.numberRoom.setError("numberRoom should not be longer than 20");
            return false;
        } else {
            binding.numberRoom.setEnabled(false);
            return true;
        }
    }

    public boolean rateRoomValidate() {
        String passwordStr = binding.rateRoom.getEditText().getText().toString().trim();

        if (passwordStr.isEmpty()) {
            binding.rateRoom.setError("rateRoom cannot be empty");
            return false;
        } else {
            binding.rateRoom.setEnabled(false);
            return true;
        }
    }


    public boolean descriptionRoomValidate() {
        String phoneStr = binding.descrptionRoom.getEditText().getText().toString().trim();

        if (phoneStr.isEmpty()) {
            binding.descrptionRoom.setError("Description cannot be empty");
            return false;
        } else {
            binding.descrptionRoom.setEnabled(false);
            return true;
        }
    }

    public boolean numDayRoomValidate() {
        // username @ servise provider    domain
        String emailStr = binding.numberDayRoom.getEditText().getText().toString().trim();
        if (emailStr.isEmpty()) {
            binding.numberDayRoom.setError("numberDay cannot be empty");
            return false;
        } else {
            binding.numberDayRoom.setEnabled(false);
            return true;
        }
    }

    public boolean priceRoomValidate() {
        // username @ servise provider    domain
        String emailStr = binding.priceRoom.getEditText().getText().toString().trim();
        if (emailStr.isEmpty()) {
            binding.priceRoom.setError("priceRoom cannot be empty");
            return false;
        } else {
            binding.priceRoom.setEnabled(false);
            return true;
        }
    }

}