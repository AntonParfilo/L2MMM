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

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommentsFragment extends Fragment {

    public String user_name;
    public String user_image;
    public String comment;
    public String date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Добавлять во все активити и фрагменты для ориентации экрана
        setRetainInstance(true);

        // Получаем наш фрагмент
        View view  = inflater.inflate(R.layout.coment_fragment, null);
        TextView view_date = (TextView) view.findViewById(R.id.date);
        TextView view_comment = (TextView) view.findViewById(R.id.comment);
        TextView view_user_name = (TextView) view.findViewById(R.id.user_name);
        ImageView view_user_image = (ImageView) view.findViewById(R.id.user_image);
        // Переформатируем дату в нужный вариант
        Date date1 = new Date();
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        try {
            date1 = input.parse(date);
            date = output.format(date1);
        }catch (ParseException e1) { }

        view_comment.setText(comment);
        view_user_name.setText(user_name);
        view_date.setText(date);
        Picasso.with(getContext()).load(user_image).into(view_user_image);




        return view;
    }

    public CommentsFragment(String user_name, String user_image, String comment, String date) {
        this.user_name = user_name;
        this.user_image = user_image;
        this.comment = comment;
        this.date = date;
    }
}
