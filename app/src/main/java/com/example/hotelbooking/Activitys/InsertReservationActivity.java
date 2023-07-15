package com.example.hotelbooking.Activitys;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.apis.RetrofitAddReservation;
import com.example.hotelbooking.databinding.ActivityInsertReservationBinding;
import com.example.hotelbooking.model.Reservation;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertReservationActivity extends AppCompatActivity {

    ActivityInsertReservationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertReservationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //.... code
        clickItem();
    }

    private void clickItem() {
        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCheckOut();
            }
        });
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCheckIn();
            }
        });
        binding.btnBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!countPersonValidate() || !checkInValidate() || !checkOutValidate()) {
                    return;
                } else {
                    insertHotel();
                }
            }
        });
    }

    private void openDialogCheckIn() {
        Calendar date = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.CheckIn.getEditText().setText(year + " /" + month + " /" + dayOfMonth);
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private void openDialogCheckOut() {
        Calendar date = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.CheckOut.getEditText().setText(year + " /" + month + " /" + dayOfMonth);
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    public boolean countPersonValidate() {
        String usernameStr = binding.CountPerson.getEditText().getText().toString().trim();
        if (usernameStr.isEmpty()) {
            binding.CountPerson.setError("CountPerson cannot be empty");
            return false;
        } else if (usernameStr.length() > 20) {
            binding.CountPerson.setError("CountPerson should not be longer than 20");
            return false;
        } else {
            binding.CountPerson.setEnabled(false);
            return true;
        }
    }

    public boolean checkInValidate() {
        String passwordStr = binding.CheckIn.getEditText().getText().toString().trim();

        if (passwordStr.isEmpty()) {
            binding.CheckIn.setError("CheckIn cannot be empty");
            return false;
        } else {
            binding.CheckIn.setEnabled(false);
            return true;
        }
    }


    public boolean checkOutValidate() {
        String phoneStr = binding.CheckOut.getEditText().getText().toString().trim();

        if (phoneStr.isEmpty()) {
            binding.CheckOut.setError("CheckOut cannot be empty");
            return false;
        } else {
            binding.CheckOut.setEnabled(false);
            return true;
        }
    }

    private void insertHotel() {
        //retrieve data from Edit texts
        int id_user = getIntent().getIntExtra("id_user", 0);
        int id_room = getIntent().getIntExtra("id_room", 0);
        String CountPerson = binding.CountPerson.getEditText().getText().toString().trim();
        String CheckIn = binding.CheckIn.getEditText().getText().toString().trim();
        String CheckOut = binding.CheckOut.getEditText().getText().toString().trim();
        //Here we will handle the http request to insert user to mysql db
        Call<Reservation> call = RetrofitAddReservation.getInstance().getMyApi().insertReservation(id_user, id_room, CountPerson, CheckIn, CheckOut);
        call.enqueue(new Callback<Reservation>() {

            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                finish();
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}