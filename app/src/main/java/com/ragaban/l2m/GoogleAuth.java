package com.ragaban.l2m;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GoogleAuth extends MainActivity {
    static DBHelper dbHelper;



    public static void registration(Context context, String user_name, String user_email, String user_photo, ImageView google_icon, View auth_btn, TextView title_name, TextView title_email){
        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("photo", user_photo);
        data.put("name", user_name);
        data.put("email", user_email);
        //записываем пользователя в базу
        db.insert("user_pasport", null, data);
        Picasso.with(context.getApplicationContext()).load(user_photo).into(google_icon);
        ((ViewGroup) auth_btn.getParent()).removeView(auth_btn);
        title_email.setText(user_email);
        title_name.setText(user_name);

    }

    public static Boolean ifLogged(Context context, ImageView google_icon, View auth_btn, TextView title_name, TextView title_email){
        boolean result = false;
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("user_pasport", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            result = true;
            String user_name = cursor.getString(1);
            String user_email = cursor.getString(2);
            String user_photo = cursor.getString(0);
            Picasso.with(context.getApplicationContext()).load(user_photo).into(google_icon);
            ((ViewGroup) auth_btn.getParent()).removeView(auth_btn);
            title_email.setText(user_email);
            title_name.setText(user_name);
        }
        db.close();
        return result;
    }
    public static String[] getUser(Context context){
        String[] user = new String[3];
        user[0] = "none";
        user[1] = "none";
        user[2] = "none";
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("user_pasport", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            user[0] = cursor.getString(0);
            user[1] = cursor.getString(1);
            user[2] = cursor.getString(2);
            db.close();
            return  user;
        }
        else return user;

    }

    public static String getUserName(Context context){
        String user_name = "none";
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("user_pasport", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            user_name = cursor.getString(1);
        }
        db.close();
        return user_name;
    }
}
