package com.example.hotelbooking.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.apis.RetrofitSignUp;
import com.example.hotelbooking.databinding.ActivitySignUpBinding;
import com.example.hotelbooking.model.Result;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    public TextInputLayout name, password, email, phone;
    public Button RegBtn;
    public String Name, Pass, Email, Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //... code
        onClickItem();
    }

    private void onClickItem() {
        binding.btnBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call insertUser method
                if (!usernameValidate() || !emailValidate() || !passwordValidate() || !phoneValidate()) {
                    return;
                } else {
                    insertUser();
                }
            }
        });
    }

    private void insertUser() {
        //retrieve data from Edit texts
        Name = binding.userSignUp.getEditText().getText().toString().trim();
        Pass = binding.passwordSignUp.getEditText().getText().toString().trim();
        Email = binding.emailSignUp.getEditText().getText().toString().trim();
        Phone = binding.phoneSignUp.getEditText().getText().toString().trim();
        //Here we will handle the http request to insert user to mysql db
        Call<Result> call = RetrofitSignUp.getInstance().getMyApi().SignUp(Name, Pass, Email, Phone);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError() == true) {
                    Log.d("something goes wrong --- > ", response.body().getMessage());
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "something goes wrong", Toast.LENGTH_SHORT).show();

                } else if (response.body().getError() == false) {
                    Log.d("Response ---> ", "User registered successfully");
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Failed to Insert Data ---> ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to Insert Data --> " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }//End insertUser method

    public boolean usernameValidate() {
        String usernameStr = binding.userSignUp.getEditText().getText().toString().trim();

        if (usernameStr.isEmpty()) {
            binding.userSignUp.setError("Username cannot be empty");
            return false;

        } else if (usernameStr.length() > 20) {
            binding.userSignUp.setError("Username should not be longer than 20");
            return false;

        } else {
            binding.userSignUp.setEnabled(false);
            return true;
        }


    }

    public boolean passwordValidate() {
        String passwordStr = binding.passwordSignUp.getEditText().getText().toString().trim();

        if (passwordStr.isEmpty()) {
            binding.passwordSignUp.setError("password cannot be empty");
            return false;
        } else if ((passwordStr.length() != 8)) {
            binding.passwordSignUp.setError("A password consisting of 8 characters");
            return false;

        } else {
            binding.passwordSignUp.setEnabled(false);
            return true;
        }

    }


    public boolean phoneValidate() {
        String phoneStr = binding.phoneSignUp.getEditText().getText().toString().trim();

        if (phoneStr.isEmpty()) {
            binding.phoneSignUp.setError("phone cannot be empty");
            return false;

        } else if (phoneStr.length() != 9) {
            binding.phoneSignUp.setError("It should be 9 digits long");
            return false;
        } else {
            String idPhone = phoneStr.substring(0, 2);
            if (!idPhone.equals("59") && !idPhone.equals("56")) {
                binding.phoneSignUp.setError("sorry your number not valid");
                return false;

            }
            binding.phoneSignUp.setEnabled(false);
            return true;
        }
    }

    public boolean emailValidate() {
        // username @ servise provider    domain
        String emailStr = binding.emailSignUp.getEditText().getText().toString().trim();
        if (emailStr.isEmpty()) {
            binding.emailSignUp.setError("email cannot be empty");
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            binding.emailSignUp.setError("Error the syntax emai");
            return false;
        } else {
            binding.emailSignUp.setEnabled(false);
            return true;
        }
    }
}