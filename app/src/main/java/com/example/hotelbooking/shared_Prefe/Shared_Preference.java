package com.example.hotelbooking.shared_Prefe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.hotelbooking.Activitys.AdminActivity;
import com.example.hotelbooking.Activitys.FirstActivity;
import com.example.hotelbooking.Activitys.MainActivity;
import com.example.hotelbooking.Activitys.SignInActivity;
import com.example.hotelbooking.model.User;

public class Shared_Preference {

    private static final String SHARED_PREF_NAME = "EStoreSharedPre";
    private static final String KEY_ID = "keyId";
    private static final String KEY_NAME = "keyName";
    private static final String KEY_EMAIL = "keyEmail";
    private static final String KEY_PASSWORD = "keyPassword";
    private static final String KEY_PHONE = "keyPhone";
    private static final String KEY_TYPE = "keyType";
    private static Shared_Preference mInstance;
    private static Context mCtx;

    public Shared_Preference(Context context) {
        mCtx = context;
    }

    // synchronized
    // تحمي الشيرد برفرنس ببياناته بحيث ما يتم الوصول الها الا باستخدام ثريد واحدة فقط
    public static synchronized Shared_Preference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Shared_Preference(context);
        }
        return mInstance;
    }

    public void userLogin(User user) {
        // MODE_PRIVATE
        // منع التطبيقات الثانية من مشاركة بيانات هذا التطبيق
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_TYPE, user.getType());
        editor.apply();
    }

    public User getUsers() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt(KEY_ID, 0), sharedPreferences.getString(KEY_NAME, ""), sharedPreferences.getString(KEY_EMAIL, ""), sharedPreferences.getString(KEY_PASSWORD, ""), sharedPreferences.getString(KEY_PHONE, ""), sharedPreferences.getString(KEY_TYPE, ""));
    }

    public void isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("keyType", null);
        if (type != null) {
            if (type.equals("admin")) {
                Intent i = new Intent(mCtx, AdminActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(i);
            } else if (type.equals("user")) {
                Intent i = new Intent(mCtx, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(i);
            }
        } else {
            Intent i = new Intent(mCtx, FirstActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(i);
        }

    }

    public void LogOut() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent i = new Intent(mCtx, SignInActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(i);
    }

    public void userUpdate(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_TYPE, "user");
        editor.apply();
    }
}
