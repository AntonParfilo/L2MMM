package com.ragaban.l2m;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddServer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddServer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String chronicle;
    public static String backPressed= "none";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static String selectedDate = "none";
    public static Date naw_date = new Date();
    public static EditText server_address;
    public static EditText rates;
    public static View root;
    public static SimpleDateFormat output;
    public AddServer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddServer.
     */
    // TODO: Rename and change types and number of parameters
    public static AddServer newInstance(String param1, String param2) {
        AddServer fragment = new AddServer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_server, container, false);
        server_address = (EditText) root.findViewById(R.id.server_address);
        rates = (EditText) root.findViewById(R.id.rates);
        CalendarView calendarView = (CalendarView) root.findViewById(R.id.calendar);
        output = new SimpleDateFormat("yyyy-MM-dd");
        selectedDate = output.format(naw_date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                selectedDate = new StringBuilder().append(mYear)
                        .append("-").append(mMonth + 1).append("-").append(mDay)
                        .append(" ").toString();
                SimpleDateFormat input = new SimpleDateFormat("yyyy-M-d");
                output = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    naw_date = input.parse(selectedDate);
                    selectedDate = output.format(naw_date); // Это результат
                } catch (ParseException e1) { }

            }
        });

        String[] chronicle_list = {"Interlude", "Interlude+", "High-Five", "Gracia Epilogue", "Gracia Final", "Grand Crusade", "C4", "Classic", "Helios", "Salvation", "Freya", "Ertheia", "Orfen"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item1, chronicle_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) root.findViewById(R.id.chronicle_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        TextView selected = (TextView) view;
                        chronicle = selected.getText().toString();
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        backPressed = "goHome";
        return root;
    }


}
