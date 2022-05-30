package com.ragaban.l2m;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class ServerListMainFragment extends Fragment {
    public String date;

    public String server_adress;

    public String user_name;

    public String rates;

    public String start;

    public String client_mail;

    public String rating_count;

    public String stars;

    public String id;

    public String time;

    public String vip;

    public String chronicle;
    public Date naw_date = new Date();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Добавлять во все активити и фрагменты для ориентации экрана
        setRetainInstance(true);

        // Получаем наш фрагмент
        View view  = inflater.inflate(R.layout.server_list_main_fragment, null);
        TextView view_date = (TextView) view.findViewById(R.id.date);
        // Переформатируем дату в нужный вариант
        SimpleDateFormat input = new SimpleDateFormat("yyyy-M-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat output_naw = new SimpleDateFormat("yyyy-M-dd");
        String naw_date_str = output_naw.format(naw_date);
        try {
            naw_date = input.parse(naw_date_str);
            Date res = input.parse(date);
            if(res.compareTo(naw_date) >= 0){
                long diffInMillies = Math.abs(res.getTime() - naw_date.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diff == 0) {
                    date = "Сегодня";
                    view_date.setTextColor(getContext().getColor(R.color.green));
                }
                else if(diff == 1){
                    date = "Завтра";
                    view_date.setTextColor(getContext().getColor(R.color.red));
                }
                else date = output.format(res); // Это результат
            }
            if(res.compareTo(naw_date) <= 0){
                long diffInMillies = Math.abs(naw_date.getTime() - res.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if(diff == 0) {
                    date = "Сегодня";
                    view_date.setTextColor(getContext().getColor(R.color.green));
                }
                else if(diff == 1) {
                    date = "Вчера";
                    view_date.setTextColor(getContext().getColor(R.color.green));
                }
                else date = output.format(res); // Это результат
            }
        } catch (ParseException e1) { }


        view_date.setText(date);

        TextView view_server_id = (TextView) view.findViewById(R.id.id_server);
        view_server_id.setText(id);

        TextView view_address = (TextView) view.findViewById(R.id.server_address);
        view_address.setText(server_adress);

        TextView view_chronicle = (TextView) view.findViewById(R.id.chronicle);
        view_chronicle.setText(chronicle);

        ImageView vip_img = (ImageView) view.findViewById(R.id.vip_icon);
        if(vip.equals("1")){
            vip_img.setVisibility(view.VISIBLE);
            view_address.setTextColor(getResources().getColor(R.color.orange));
        }

        ImageView view_stars = (ImageView) view.findViewById(R.id.stars_title);
        switch(Integer.parseInt(stars)){
            case 1:
                view_stars.setImageDrawable(getActivity().getApplicationContext().getDrawable(R.drawable.stars1_left));
                break;
            case 2:
                view_stars.setImageDrawable(getActivity().getApplicationContext().getDrawable(R.drawable.stars2_left));
                break;
            case 3:
                view_stars.setImageDrawable(getActivity().getApplicationContext().getDrawable(R.drawable.stars3_left));
                break;
            case 4:
                view_stars.setImageDrawable(getActivity().getApplicationContext().getDrawable(R.drawable.stars4_left));
                break;
            case 5:
                view_stars.setImageDrawable(getActivity().getApplicationContext().getDrawable(R.drawable.stars5));
                break;

        }
        TextView view_rating_count = (TextView) view.findViewById(R.id.rating_count);
        view_rating_count.setText("(" + rating_count + ")");

        TextView view_rates = (TextView) view.findViewById(R.id.rates);
        if(rates.contains("RvR") || rates.contains("GvE")){
            view_rates.setText(rates);
        }
        else{
            view_rates.setText("X"+rates);
        }

        return view;
    }


    public ServerListMainFragment(String date, String server_adress, String user_name, String rates, String start, String client_mail, String rating_count, String stars, String id, String time, String vip, String chronicle) {
        this.date = date;
        this.server_adress = server_adress;
        this.user_name = user_name;
        this.rates = rates;
        this.start = start;
        this.client_mail = client_mail;
        this.rating_count = rating_count;
        this.stars = stars;
        this.id = id;
        this.time = time;
        this.vip = vip;
        this.chronicle = chronicle;
    }

}
