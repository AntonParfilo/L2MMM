package com.ragaban.l2m;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ServerApi{

    @Headers("Content-Type: application/json")
    @GET("l2m.php?key=getServer")
    Call<List<ServerList>> getAllServer(@Query("chronicle") String chronicle, @Query("rates") String rates, @Query("date") String date);

    @Headers("Content-Type: application/json")
    @GET("l2m.php?key=getAllChronicle")
    Call<List<ServerList>> getAllChronicle();

    @GET("l2m.php?key=getAllRates")
    Call<List<ServerList>> getAllRates();

    @GET("l2m.php?key=viewServer")
    Call<List<ServerList>> viewServer(@Query("server_id") int server_id);

    @GET("l2m.php?key=setRating")
    Call<String> setRating(@Query("server_id") int server_id, @Query("user_email") String user_email, @Query("rating_value") int rating_value);

    @GET("l2m.php?key=getComments")
    Call<List<Comments>> getComments(@Query("server_id") int server_id);

    @GET("l2m.php?key=setComments")
    Call<String> setComment(@Query("server_id") int server_id, @Query("user_name") String user_name, @Query("comment") String comment, @Query("date") String date, @Query("user_image") String user_image);

    @GET("l2m.php?key=addServer")
    Call<String> addServer(@Query("server_address") String server_address, @Query("rates") int rates, @Query("chronicle") String chronicle, @Query("date") String date, @Query("user_email") String user_email);

    @GET("l2m.php?key=feedback")
    Call<String> sendFeedback(@Query("user_name") String user_name, @Query("user_email") String user_email, @Query("message") String message);

    @GET("l2m.php?key=sendToken")
    Call<String> sendToken(@Query("token") String token);

}

