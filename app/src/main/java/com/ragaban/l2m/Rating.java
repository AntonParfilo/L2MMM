package com.ragaban.l2m;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Rating extends MainActivity{
    static DBHelper dbHelper;

    public static boolean ifRating(Context context, int server_id){
        boolean result = false;
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("rating", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();
           for(int i=0; i<count; i++){
               if(server_id == Integer.parseInt(cursor.getString(0))){
                   result = true;
               }
               cursor.moveToNext();
           }
        }
        db.close();
        return result;
    }

    public static void setRates(final Context context, final int server_id, final int rating_value){

        String[] user = GoogleAuth.getUser(context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ragaban.zzz.com.ua/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<String> messages = serverApi.setRating(server_id, user[2], rating_value);
        messages.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    //записываем пользователя в базу
                    dbHelper = new DBHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues data = new ContentValues();
                    data.put("server", server_id);
                    data.put("value", rating_value);
                    db.insert("rating", null, data);
                    db.close();
                } else {
                    System.out.println("response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("failure " + t);
            }
        });

    }

    public static int get_rating_stars(Context context, int server_id){
        String result = "0";
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("rating", null, "server = ?", new String[] {String.valueOf(server_id)}, null, null, null);
        if (cursor.moveToFirst()) {
           result = cursor.getString(1);
        }
        db.close();
        return Integer.parseInt(result);
    }

}
