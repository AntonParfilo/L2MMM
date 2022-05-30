package com.ragaban.l2m;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.ragaban.l2m.AddServer.backPressed;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Feedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feedback extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String down = "bottom_right";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View root;
    public static ProgressBar progressBar;
    public Feedback() {
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
    public static Feedback newInstance(String param1, String param2) {
        Feedback fragment = new Feedback();
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

        root = inflater.inflate(R.layout.fragment_feedback, container, false);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar_feedback);
        TextView button = (TextView) root.findViewById(R.id.send_feedback);
        final TextView message = (TextView) root.findViewById(R.id.messageFeedback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendFeedback(message);

            }
        });



        backPressed = "goHome";
        return root;
    }

    public void sendFeedback(TextView message){
        String[] user = GoogleAuth.getUser(getContext());
        if(user[0].contains("none")){
            Toast.makeText(getContext(), "Только для авторизированных пользователей.", Toast.LENGTH_LONG).show();
        }
        else {
            if(message.length()<3){
                Toast.makeText(getContext(), "Ваше сообщение слишком короткое.", Toast.LENGTH_LONG).show();
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ragaban.zzz.com.ua/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final ServerApi serverApi = retrofit.create(ServerApi.class);
                Call<String> messages = serverApi.sendFeedback(user[1], user[2], message.getText().toString());
                messages.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, final Response<String> response) {
                        if (response.isSuccessful()) {
                            if (response.body().contains("TRUE")) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        MainActivity ma = (MainActivity) getActivity();
                                        ma.onBackPressed();
                                    }
                                }, 2000);
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "Ошибка, возможно вы слишком часто пишете.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            System.out.println("response code " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println("failure " + t);
                    }
                });


            }




        }

    }


}
