package com.ragaban.l2m;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<Star> {
    private Activity context;
    ArrayList<Star> data = null;

    public CountryAdapter(Activity context, int resource, ArrayList<Star> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) { // этот код выполняется, когда вы нажимаете на спиннер
            LayoutInflater inflater = context.getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_item_dropdown_images, parent, false);
            Star item = data.get(position);
            ImageView myFlag = (ImageView) row.findViewById(R.id.resource);
            TextView myCountry = (TextView) row.findViewById(R.id.val);
            myFlag.setBackground(context.getResources().getDrawable(item.getStars(), context.getTheme()));
            myCountry.setText(item.getVal());
        return row;
    }
}