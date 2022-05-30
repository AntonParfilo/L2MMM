package com.ragaban.l2m;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class View_Server extends AppCompatActivity {
    CommentsFragment comment_fragment;
    FragmentTransaction ftrans;
    public static String server_name;
    public static int server_id;
    public Date naw_date = new Date();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.view_server);

        //получаем вьюхи
        final TextView server_title = (TextView) findViewById(R.id.server_name);
        final TextView ch_title = (TextView) findViewById(R.id.chronicle);
        final TextView rates_title = (TextView) findViewById(R.id.rates);
        final TextView date_title = (TextView) findViewById(R.id.date);
        final ImageView favicon_title = (ImageView) findViewById(R.id.favicon_title);
        final ImageView stars_title = (ImageView) findViewById(R.id.star_title);
        final TextView goSite = (TextView) findViewById(R.id.goWeb);


        server_id = Integer.parseInt(getIntent().getStringExtra("server_id"));
        server_name = getIntent().getStringExtra("server_name");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ragaban.zzz.com.ua/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ServerApi serverApi = retrofit.create(ServerApi.class);
        final Call<List<ServerList>> server = serverApi.viewServer(server_id);
        server.enqueue(new Callback<List<ServerList>>() {
            @Override
            public void onResponse(Call<List<ServerList>> call, Response<List<ServerList>> response) {
                if (response.isSuccessful()) {

                    // Переформатируем дату в нужный вариант
                    String date = response.body().get(0).getDate();
                    SimpleDateFormat input = new SimpleDateFormat("yyyy-M-dd");
                    SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
                    try {
                        Date res = input.parse(date);
                        date = output.format(res); // Это результат
                    } catch (ParseException e1) { }

                    String favicon_adress = "https://www.google.com/s2/favicons?domain="+response.body().get(0).getServer_adress();
                    Picasso.with(getApplicationContext()).load(favicon_adress).into(favicon_title);
                    server_title.setText(response.body().get(0).getServer_adress());
                    ch_title.setText(response.body().get(0).getChronicle());
                    rates_title.setText("X"+response.body().get(0).getRates());
                    date_title.setText(date);
                    goSite.setText(server_title.getText().toString());
                    server_name = response.body().get(0).getServer_adress();
                    switch(Integer.parseInt(response.body().get(0).getStars())){
                        case 1:
                            stars_title.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars1_left));
                            break;
                        case 2:
                            stars_title.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars2_left));
                            break;
                        case 3:
                            stars_title.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars3_left));
                            break;
                        case 4:
                            stars_title.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars4_left));
                            break;
                        case 5:
                            stars_title.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars5));
                            break;
                    }
                } else {
                    System.out.println("response code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<ServerList>> call, Throwable t) {
                System.out.println("failure " + t);
            }
        });


        ArrayList<Star> Stars = new ArrayList<Star>();
        Stars.add(new Star("1", R.drawable.stars1_right));
        Stars.add(new Star("2", R.drawable.stars2_right));
        Stars.add(new Star("3", R.drawable.stars3_right));
        Stars.add(new Star("4", R.drawable.stars4_right));
        Stars.add(new Star("5", R.drawable.stars5));
        CountryAdapter myAdapter = new CountryAdapter(this, android.R.layout.simple_spinner_item, Stars);
        Spinner spinner = findViewById(R.id.spinner_star);
        spinner.setAdapter(myAdapter);
        spinner.setSelection(2);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        TextView selected = (TextView) view;
                        selected.setTextSize(1);
                        switch(Integer.parseInt(selected.getText().toString())) {
                            case 1:
                                selected.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stars1_right, 0, 0, 0);
                                break;
                            case 2:
                                selected.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stars2_right, 0, 0, 0);
                                break;
                            case 3:
                                selected.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stars3_right, 0, 0, 0);
                                break;
                            case 4:
                                selected.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stars4_right, 0, 0, 0);
                                break;
                            case 5:
                                selected.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stars5, 0, 0, 0);
                                break;
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        boolean if_rating = Rating.ifRating(getApplicationContext(), server_id);
        if(if_rating){
            int stars_num = Rating.get_rating_stars(getApplicationContext(), server_id);
            spinner.setVisibility(View.INVISIBLE);
            spinner.setBackground(null);
            TextView set_star = (TextView) findViewById(R.id.setStar);
            set_star.setVisibility(View.INVISIBLE);
            ImageView user_stars = (ImageView) findViewById(R.id.user_stars);
            switch (stars_num){
                case 1:
                    user_stars.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars1_center));
                    break;
                case 2:
                    user_stars.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars2_center));
                    break;
                case 3:
                    user_stars.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars3_center));
                    break;
                case 4:
                    user_stars.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars4_center));
                    break;
                case 5:
                    user_stars.setImageDrawable(getApplicationContext().getDrawable(R.drawable.stars5));
                    break;
            }
        }
        else{
            TextView user_rates = (TextView) findViewById(R.id.user_rates);
            ImageView user_stars = (ImageView) findViewById(R.id.user_stars);
            user_rates.setAlpha(0);
            user_stars.setAlpha(0);
        }
        getComments();
    }


    public void setRating(View v){
        String[] user = GoogleAuth.getUser(getApplicationContext());
        if(user[0].contains("none")){
            Toast.makeText(getApplicationContext(), "Только для авторизированных пользователей.", Toast.LENGTH_LONG).show();
        }
        else {
            View progress_bar = findViewById(R.id.progressBar_view_server);
            progress_bar.setVisibility(View.VISIBLE);
            Spinner spinner = findViewById(R.id.spinner_star);
            String rates_val = spinner.getSelectedItem().toString();
            Rating.setRates(getApplicationContext(), server_id, Integer.parseInt(rates_val));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    startActivity(getIntent());
                }
            }, 2000);

        }

    }


    public void setComments(View v){
        TextView comment_text = (TextView) findViewById(R.id.comment_text);
        String[] user = GoogleAuth.getUser(getApplicationContext());
        if(user[0].contains("none")){
            Toast.makeText(getApplicationContext(), "Только для авторизированных пользователей.", Toast.LENGTH_LONG).show();
        }
        else {
            if (comment_text.length() < 3) {
                Toast.makeText(getApplicationContext(), "Сообщение должно быть не менее 3 символов.", Toast.LENGTH_LONG).show();
            } else if (comment_text.length() > 500) {
                Toast.makeText(getApplicationContext(), "Сообщение должно быть не более 500 символов.", Toast.LENGTH_LONG).show();
            } else {
                View progress_bar = findViewById(R.id.progressBar_sendComment);
                progress_bar.setVisibility(View.VISIBLE);
                String user_name = user[1];
                String user_image = user[0];
                String comment = comment_text.getText().toString();
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = output.format(naw_date);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ragaban.zzz.com.ua/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final ServerApi serverApi = retrofit.create(ServerApi.class);
                Call<String> messages = serverApi.setComment(server_id, user_name, comment, date, user_image);
                messages.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            if(response.body().contains("Armor")){
                                Toast.makeText(getApplicationContext(), "Слишком часто пишете.", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            System.out.println("response code " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println("failure " + t);
                    }
                });
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                        startActivity(getIntent());
                    }
                }, 2000);
            }
        }
    }

    public void getComments(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ragaban.zzz.com.ua/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<List<Comments>> messages = serverApi.getComments(server_id);
        messages.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size() > 0){
                        for(int i=0;i<response.body().size();i++){

                            comment_fragment = new CommentsFragment(response.body().get(i).getUser_name(), response.body().get(i).getUser_image(), response.body().get(i).getComment(), response.body().get(i).getDate());
                            ftrans = getSupportFragmentManager().beginTransaction();
                            ftrans.add(R.id.comments_container, comment_fragment);
                            ftrans.commit();

                        }
                    }
                } else {
                    System.out.println("response code " + response.code());

                }
            }
            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                System.out.println("failure " + t);
            }
        });
    }

    public void checkComment(View v){
        String[] user = GoogleAuth.getUser(getApplicationContext());
        if(user[0].contains("none")){
            Toast.makeText(getApplicationContext(), "Только для авторизированных пользователей.", Toast.LENGTH_LONG).show();
        }
        else{

        }
    }




}
