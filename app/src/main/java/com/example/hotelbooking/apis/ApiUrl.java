package com.example.hotelbooking.apis;

public interface ApiUrl {
    String GET_HOTELS = "https://10.0.2.2/db/Hotel/DisplayHotel.php/";
    String GET_ROOM = "https://10.0.2.2/db/Hotel/DisplayRoom.php/";

    /* URL  Sign In*/
    String SIGN_IN = "http://10.0.2.2/db/Hotel/SignIn.php/";

    // Sign Up Api
    String SIGN_UP = "http://10.0.2.2/db/Hotel/SignUp.php/";
    String UPDATE_USER = "http://10.0.2.2/db/Hotel/UpdateUser.php/";
    // GET IMAGE USER
    String GET_IMAGE_USER = "http://10.0.2.2/db/Hotel/getImageProfile.php/";

    // GET ALL HOTELS
    String ADD_HOTEL = "http://10.0.2.2/db/Hotel/InsertHotel.php/";
    String ADD_ROOM = "http://10.0.2.2/db/Hotel/insertRoom.php/";
    String DELETE_ROOM = "http://10.0.2.2/db/Hotel/deleteRoom.php/";

    String DELETE_HOTEL = "http://10.0.2.2/db/Hotel/deleteHotel.php/";
    String UPDATE_HOTEL = "http://10.0.2.2/db/Hotel/updateHotel.php/";
    String UPDATE_ROOM = "http://10.0.2.2/db/Hotel/updateRoom.php/";

    String INSERT_Reservation = "http://10.0.2.2/db/Hotel/insertReservation.php/";
    String GET_Reservation = "http://10.0.2.2/db/Hotel/DispalyReservation.php/";
    String GET_ALL_Reservations = "http://10.0.2.2/db/Hotel/DispalyReservationAdmin.php/";
    String UPDATE_Reservation = "http://10.0.2.2/db/Hotel/updateReservationAdmin.php/";


}
