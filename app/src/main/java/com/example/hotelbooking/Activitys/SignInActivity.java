package com.example.hotelbooking.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.R;
import com.example.hotelbooking.apis.RetrofitSignIn;
import com.example.hotelbooking.databinding.ActivitySignInBinding;
import com.example.hotelbooking.model.Result;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.shared_Prefe.Shared_Preference;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    private TextInputLayout EmailTextInputLayout, PasswordTextInputLayout;
    private Button buttonSignIn;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //..... code
        init();
        onClickItem();
    }

    private void init() {
        EmailTextInputLayout = (TextInputLayout) findViewById(R.id.phoneSignUp);
        PasswordTextInputLayout = (TextInputLayout) findViewById(R.id.passwordSignIn);
    }

    private void onClickItem() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phoneValidate() || !passwordValidate()) {
                    return;
                } else {
                    userSignIn();
                }
            }
        });
        binding.goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call insertUser method
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });
    }

    private void userSignIn() {
        binding.progressSignIn.setVisibility(View.VISIBLE);
        String phone = binding.phoneSignIn.getEditText().getText().toString().trim();
        String password = binding.passwordSignIn.getEditText().getText().toString().trim();
        Call<Result> call = RetrofitSignIn.getInstance().getMyApi().SignIn(phone, password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.body().getError()) {
                    binding.progressSignIn.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Welcome " + response.body().getUser().getName(), Toast.LENGTH_LONG).show();
                    User user = new User((int)
                            response.body().getUser().getId(),
                            response.body().getUser().getName(),
                            response.body().getUser().getEmail(),
                            response.body().getUser().getPassword(),
                            response.body().getUser().getPhone()
                            , response.body().getUser().getType());
                    if (response.body().getUser().getType().equals("admin")) {
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    } else {
                        Intent i = new Intent(SignInActivity.this, MainActivity.class);
                        Shared_Preference.getInstance(getApplicationContext()).userLogin(user);
                        startActivity(i);
                        finish();
                    }
                } else {
                    binding.progressSignIn.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Invalid email or password ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Retrofit ERROR -->", t.getMessage());
                binding.progressSignIn.setVisibility(View.INVISIBLE);
            }
        });
    }

    public boolean passwordValidate() {
        String passwordStr = binding.passwordSignIn.getEditText().getText().toString().trim();

        if (passwordStr.isEmpty()) {
            binding.passwordSignIn.setError("password cannot be empty");
            return false;
        } else if ((passwordStr.length() != 8)) {
            binding.passwordSignIn.setError("A password consisting of 8 characters");
            return false;

        } else {
            binding.passwordSignIn.setEnabled(false);
            return true;
        }

    }


    public boolean phoneValidate() {
        String phoneStr = binding.phoneSignIn.getEditText().getText().toString().trim();

        if (phoneStr.isEmpty()) {
            binding.phoneSignIn.setError("phone cannot be empty");
            return false;

        } else if (phoneStr.length() != 9) {
            binding.phoneSignIn.setError("It should be 9 digits long");
            return false;
        } else {
            String idPhone = phoneStr.substring(0, 2);
            if (!idPhone.equals("59") && !idPhone.equals("56")) {
                binding.phoneSignIn.setError("sorry your number not valid");
                return false;

            }
            binding.phoneSignIn.setEnabled(false);
            return true;
        }
    }


}