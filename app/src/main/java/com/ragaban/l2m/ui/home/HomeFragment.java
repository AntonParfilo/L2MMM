package com.ragaban.l2m.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ragaban.l2m.AllChronicle;
import com.ragaban.l2m.MainActivity;
import com.ragaban.l2m.R;
import com.ragaban.l2m.ServerApi;
import com.ragaban.l2m.ServerList;
import com.ragaban.l2m.ServerListMainFragment;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ServerListMainFragment server_list_main_fragment;
    FragmentTransaction ftrans;

    int server_list_count;
    String TAG = "TAG:";
    public String[] ch_list;
    public String[] rates_list;
    public String ch1 = "ALL";
    public String rates1 = "ALL";
    public String date1 = "AFTER";
    public int use_db_ch = 0;
    public int use_db_rates = 0;
    public int use_db_date = 0;
    public View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/fundamentalcondensedregular.ttf");
        root = inflater.inflate(R.layout.fragment_home, container, false);

        final Toolbar toolbar_home = root.findViewById(R.id.toolbar_home);
        toolbar_home.setNavigationIcon(R.drawable.ic_menu_btn);
        final MainActivity main = (MainActivity)this.getActivity();
        toolbar_home.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.onSupportNavigateUp();
            }
        });
        // прячем табличку "НЕТ СОВПАДЕНИЙ"
        TextView no_server = (TextView) root.findViewById(R.id.no_servers);
        no_server.setAlpha(0);
        // прячем кнопку НАВЕРХ
        final ImageView toUp = (ImageView) root.findViewById(R.id.toUp);
        toUp.animate()
                .setDuration(100)
                .translationX(200);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://ragaban.zzz.com.ua/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<List<ServerList>> ch = serverApi.getAllChronicle();
        ch.enqueue(new Callback<List<ServerList>>() {
            @Override
            public void onResponse(Call<List<ServerList>> call, Response<List<ServerList>> response) {
                if (response.isSuccessful()) {
                    ch_list = new String[response.body().size()];
                    server_list_count =  response.body().size();
                    for (int i=0; i<server_list_count; i++) {
                        ch_list[i] = response.body().get(i).getChronicle();
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, ch_list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spinner = (Spinner) root.findViewById(R.id.select_chronilce);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                    TextView selected = (TextView) view;
                                    selected.setTypeface(typeface);
                                    if(use_db_ch == 0) {
                                        System.out.println("пропускаем хроники");
                                        use_db_ch++;
                                    }
                                    else get_database(parent);
                                }
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                } else {
                    System.out.println("response code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<ServerList>> call, Throwable t) {
                System.out.println("failure Ch " + t);
            }
        });

        Call<List<ServerList>> rates = serverApi.getAllRates();
        rates.enqueue(new Callback<List<ServerList>>() {
            @Override
            public void onResponse(Call<List<ServerList>> call, Response<List<ServerList>> response) {
                if (response.isSuccessful()) {
                    rates_list = new String[response.body().size()];
                    server_list_count =  response.body().size();
                    for (int i=0; i<server_list_count; i++) {
                        rates_list[i] = response.body().get(i).getRates();
                    }

                    ArrayAdapter<String> adapter_rates = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, rates_list);
                    adapter_rates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spinner_rates = (Spinner) root.findViewById(R.id.select_rates);
                    spinner_rates.setAdapter(adapter_rates);
                    spinner_rates.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                    TextView selected = (TextView) view;
                                    selected.setTypeface(typeface);
                                    if(use_db_rates == 0) {
                                        System.out.println("пропускаем рейты");
                                        use_db_rates++;
                                    }
                                    else get_database(parent);
                                }
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                } else {
                    System.out.println("response code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<ServerList>> call, Throwable t) {
                System.out.println("failure rates " + t);
            }
        });
        String[] date_list = {"Скоро открытие", "Уже открылись"};
        ArrayAdapter<String> adapter_date = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, date_list);
        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner_date = (Spinner) root.findViewById(R.id.select_date);
        spinner_date.setAdapter(adapter_date);
        int spinner3_count = spinner_date.getCount();

        spinner_date.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        TextView selected = (TextView) view;
                        selected.setTypeface(typeface);
                        if(use_db_date == 0 ) {
                            System.out.println("пропускаем дату");
                            use_db_date++;
                        }
                        else get_database(parent);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        final ScrollView scrollView = (ScrollView) root.findViewById(R.id.scrollView2);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(scrollView.getScrollY() > 100) {

                    toolbar_home.animate()
                            .setDuration(100)
                            .translationY(200);
                    toUp.animate()
                            .setDuration(100)
                            .translationX(0);
                }
                else {
                    toUp.animate()
                            .setDuration(100)
                            .translationX(200);
                    toolbar_home.animate()
                            .setDuration(100)
                            .translationY(0);
                }
            }
        });


        TextView view = (TextView) root.findViewById(R.id.chronicle);
        get_database(view);
        toUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollView scrollView = (ScrollView) root.findViewById(R.id.scrollView2);
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });




        return root;
    }

    public void get_database(View view){
        Spinner spinner_ch = root.findViewById(R.id.select_chronilce);
        String ch = (String) spinner_ch.getSelectedItem();
        Spinner spinner_rates = root.findViewById(R.id.select_rates);
        String rates = (String) spinner_rates.getSelectedItem();
        Spinner spinner_date = root.findViewById(R.id.select_date);
        String date = (String) spinner_date.getSelectedItem();
        if(ch == null) ch = "ALL";
        if(ch.contains("Все")) ch = "ALL";
        if(rates == null) rates = "ALL";
        if(rates.contains("Все")) rates = "ALL";
        if(date == null) date = "AFTER";
        if(date.contains("Скоро открытие")) date = "AFTER"; else date = "BEFORE";

        Log.d(TAG, "ch: "+ ch, null);
        Log.d(TAG, "rates: "+ rates, null);
        Log.d(TAG, "date: "+ date, null);

        TextView no_server = (TextView) root.findViewById(R.id.no_servers);
        no_server.setAlpha(0);
        LinearLayout container = (LinearLayout) root.findViewById(R.id.server_list_main_container);
        container.removeAllViews();
        View progressBar = (View) root.findViewById(R.id.progressBar_main); progressBar.setAlpha(1);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ragaban.zzz.com.ua/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<List<ServerList>> messages = serverApi.getAllServer(ch, rates, date);
        messages.enqueue(new Callback<List<ServerList>>() {
            @Override
            public void onResponse(Call<List<ServerList>> call, Response<List<ServerList>> response) {
                if (response.isSuccessful()) {
                    ch_list = new String[response.body().size()];
                    server_list_count =  response.body().size();
                    if(server_list_count == 0){
                        TextView no_server = (TextView) root.findViewById(R.id.no_servers);
                        View progressBar = (View) root.findViewById(R.id.progressBar_main); progressBar.setAlpha(0);
                        no_server.setAlpha(1);
                    }
                    for (int i=0; i<server_list_count; i++){
                        server_list_main_fragment = new ServerListMainFragment(response.body().get(i).getDate(), response.body().get(i).getServer_adress(), response.body().get(i).getUser_name(), response.body().get(i).getRates(), response.body().get(i).getStart(), response.body().get(i).getClient_mail(), response.body().get(i).getRating_count(), response.body().get(i).getStars(), response.body().get(i).getId(), response.body().get(i).getTime(), response.body().get(i).getVip(), response.body().get(i).getChronicle());
                        ftrans = getChildFragmentManager().beginTransaction();
                        ftrans.add(R.id.server_list_main_container, server_list_main_fragment);
                        ftrans.commit();
                        View progressBar = (View) root.findViewById(R.id.progressBar_main); progressBar.setAlpha(0);
                    }
                } else {
                    System.out.println("response code " + response.code());

                }
            }
            @Override
            public void onFailure(Call<List<ServerList>> call, Throwable t) {
                System.out.println("failure server " + t);
            }
        });
    }





}
