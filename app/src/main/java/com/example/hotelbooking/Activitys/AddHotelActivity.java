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

import com.example.hotelbooking.apis.RetrofitAddHotel;
import com.example.hotelbooking.databinding.ActivityAddHotelBinding;
import com.example.hotelbooking.model.Hotel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHotelActivity extends AppCompatActivity {

    ActivityAddHotelBinding binding;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //... code Add hotel Admin
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
        clickItem();
    }

    private void clickItem() {
        binding.btnBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameValidate() || !rateValidate() || !locationValidate() || !phoneValidate()) {
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
            String Name = binding.nameHotel.getEditText().getText().toString().trim();
            String Rate = binding.rateHotel.getEditText().getText().toString().trim();
            String Location = binding.locationHotel.getEditText().getText().toString().trim();
            String Phone = binding.phoneHotel.getEditText().getText().toString().trim();
            //Here we will handle the http request to insert user to mysql db
            Call<Hotel> call = RetrofitAddHotel.getInstance().getMyApi().InsertHotel(Name, Rate, Location, Phone, base46Image);
            call.enqueue(new Callback<Hotel>() {

                @Override
                public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Hotel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
        }
    }//End insertHotel method


    public boolean nameValidate() {
        String usernameStr = binding.nameHotel.getEditText().getText().toString().trim();
        if (usernameStr.isEmpty()) {
            binding.nameHotel.setError("Username cannot be empty");
            return false;
        } else if (usernameStr.length() > 20) {
            binding.nameHotel.setError("Username should not be longer than 20");
            return false;
        } else {
            binding.nameHotel.setEnabled(false);
            return true;
        }
    }

    public boolean rateValidate() {
        String passwordStr = binding.rateHotel.getEditText().getText().toString().trim();

        if (passwordStr.isEmpty()) {
            binding.rateHotel.setError("password cannot be empty");
            return false;
        } else {
            binding.rateHotel.setEnabled(false);
            return true;
        }
    }


    public boolean phoneValidate() {
        String phoneStr = binding.phoneHotel.getEditText().getText().toString().trim();

        if (phoneStr.isEmpty()) {
            binding.phoneHotel.setError("phone cannot be empty");
            return false;

        } else if (phoneStr.length() != 9) {
            binding.phoneHotel.setError("It should be 9 digits long");
            return false;
        } else {
            String idPhone = phoneStr.substring(0, 2);
            if (!idPhone.equals("59") && !idPhone.equals("56")) {
                binding.phoneHotel.setError("sorry your number not valid");
                return false;

            }
            binding.phoneHotel.setEnabled(false);
            return true;
        }
    }

    public boolean locationValidate() {
        // username @ servise provider    domain
        String emailStr = binding.locationHotel.getEditText().getText().toString().trim();
        if (emailStr.isEmpty()) {
            binding.locationHotel.setError("email cannot be empty");
            return false;
        } else {
            binding.locationHotel.setEnabled(false);
            return true;
        }
    }


}