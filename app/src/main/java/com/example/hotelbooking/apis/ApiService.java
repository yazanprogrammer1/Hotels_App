package com.example.hotelbooking.apis;


import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.Reservation;
import com.example.hotelbooking.model.Result;
import com.example.hotelbooking.model.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("DisplayHotel.php")
    Call<List<Hotel>> DisplayHotel();

    @GET("DisplayRoom.php")
    Call<List<Room>> DisplayRoom(
            @Query("id_hotel") int id_hotel
    );

    /* the SignIn call */
    @FormUrlEncoded
    @POST("SignIn.php")
    Call<Result> SignIn(
            @Field("phone") String phone,
            @Field("password") String password
    );

    /*The SignUp Call */
    @FormUrlEncoded
    @POST("SignUp.php")
    Call<Result> SignUp(
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") String phone

    );

    @FormUrlEncoded
    @POST("InsertHotel.php")
    Call<Hotel> InsertHotel(
            @Field("name") String name,
            @Field("rating") String rating,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("image") String image
    );

    // add room
    @FormUrlEncoded
    @POST("insertRoom.php")
    Call<Room> insertRoom(
            @Field("number") String number,
            @Field("count_day_available") String count_day_available,
            @Field("price") String price,
            @Field("descrption") String descrption,
            @Field("rating") String rating,
            @Field("hotel_id") int hotel_id,
            @Field("image") String image

    );

    // update room
    @FormUrlEncoded
    @POST("updateRoom.php")
    Call<Room> updateRoom(
            @Field("id") int id,
            @Field("number") String number,
            @Field("count_day_available") String count_day_available,
            @Field("price") String price,
            @Field("descrption") String descrption,
            @Field("rating") String rating,
            @Field("image") String image
    );

    // delete hotel
    @FormUrlEncoded
    @POST("deleteHotel.php")
    Call<Hotel> deleteHotel(
            @Field("id") int id
    );


    // delete room
    @FormUrlEncoded
    @POST("deleteRoom.php")
    Call<Room> deleteRoom(
            @Field("id") int id
    );


    // update hotel
    @FormUrlEncoded
    @POST("updateHotel.php")
    Call<Hotel> updateHotel(
            @Field("id") int id,
            @Field("name") String name,
            @Field("rating") String rating,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("image") String image

    );

    // add to cart
    @FormUrlEncoded
    @POST("AddToCart.php")
    Call<Result> addCart(
            @Field("idUser") int idUser,
            @Field("idProduct") int idProduct,
            @Field("numOrder") int numOrder
    );


    // get Cart Products
    @FormUrlEncoded
    @POST("UpdateUser.php")
    Call<Result> updateUser(
            @Field("id") int id,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("imgs") String image

    );

    @GET("getImageProfile.php")
    Call<Result> getImageProfile(
            @Query("user_id") int id
    );


    @FormUrlEncoded
    @POST("insertReservation.php")
    Call<Reservation> insertReservation(
            @Field("id_users") int id_users,
            @Field("id_rooms") int id_rooms,
            @Field("count_person") String count_person,
            @Field("check_in") String check_in,
            @Field("check_out") String check_out
    );

    @GET("DispalyReservation.php")
    Call<List<Reservation>> DispalyReservation(
            @Query("user_id") int user_id
    );

    @GET("DispalyReservationAdmin.php")
    Call<List<Reservation>> DispalyReservationAdmin();

    // update Reservation
    @FormUrlEncoded
    @POST("updateReservationAdmin.php")
    Call<Reservation> updateReservationAdmin(
            @Field("id") int id
    );
}
