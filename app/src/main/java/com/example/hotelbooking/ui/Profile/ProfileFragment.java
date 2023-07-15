package com.example.hotelbooking.ui.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotelbooking.R;
import com.example.hotelbooking.apis.RetrofitGetImageProfile;
import com.example.hotelbooking.apis.RetrofitUpdateUser;
import com.example.hotelbooking.databinding.FragmentProfileBinding;
import com.example.hotelbooking.model.Result;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.shared_Prefe.Shared_Preference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    public String Pass, Email, Phone;
    Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //..... code
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent ii = result.getData();
                            Uri uri = ii.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                                binding.imageProfile.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
        setHeaderInfo();
        binding.btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared_Preference.getInstance(requireActivity()).LogOut();
            }
        });
        binding.btnUpdate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                binding.editEmail.setEnabled(true);
                binding.editPassword.setEnabled(true);
                binding.editPhone.setEnabled(true);
                return true;
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUpdate();
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


    }

    private void userUpdate() {
        //retrieve data from Edit texts
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String base = null;
        if (bitmap == null) {
            BitmapDrawable drawable = (BitmapDrawable) binding.imageProfile.getDrawable();
            bitmap = drawable.getBitmap();
            binding.imageProfile.setImageBitmap(bitmap);
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        final String base46Image = Base64.encodeToString(bytes, Base64.DEFAULT);
        int id = Shared_Preference.getInstance(requireActivity()).getUsers().getId();
        Pass = binding.editPassword.getText().toString().trim();
        Email = binding.editEmail.getText().toString().trim();
        Phone = binding.editPhone.getText().toString().trim();

        //Here we will handle the http request to insert user to mysql db
        Call<Result> call = RetrofitUpdateUser.getInstance().getMyApi().updateUser(id, Email, Pass, Phone, base46Image);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError() == true) {

                } else if (response.body().getError() == false) {
                    Log.d("Response ---> ", "User Updated successfully");
                    Toast.makeText(requireActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    User user = new User((int)
                            Shared_Preference.getInstance(requireActivity()).getUsers().getId(),
                            response.body().getUser().getName(),
                            response.body().getUser().getEmail(),
                            response.body().getUser().getPassword(),
                            response.body().getUser().getPhone()
                            , response.body().getUser().getType());
                    Shared_Preference.getInstance(requireActivity()).userUpdate(user);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Failed to Insert Data ---> ", t.getMessage());
                Toast.makeText(requireActivity(), "Failed to Update Data --> " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //End update method
    }


    public void setHeaderInfo() {
        int id = Shared_Preference.getInstance(requireActivity()).getUsers().getId();
        String name = Shared_Preference.getInstance(requireActivity()).getUsers().getName();
        String email = Shared_Preference.getInstance(requireActivity()).getUsers().getEmail();
        String password = Shared_Preference.getInstance(requireActivity()).getUsers().getPassword();
        String phone = Shared_Preference.getInstance(requireActivity()).getUsers().getPhone();
        binding.nameUser.setText(name);
        binding.editPassword.setText(password);
        binding.editEmail.setText(email);
        binding.editPhone.setText(phone);
        getImageUser(id);
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
                        .into(binding.imageProfile);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}