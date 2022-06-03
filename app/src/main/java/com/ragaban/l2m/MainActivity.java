package com.ragaban.l2m;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static com.ragaban.l2m.AddServer.backPressed;
import static com.ragaban.l2m.AddServer.chronicle;
import static com.ragaban.l2m.AddServer.selectedDate;
import static com.ragaban.l2m.Feedback.progressBar;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private static long back_pressed;
    int RC_SIGN_IN = 63;
    String TAG = "Google Sign-In";
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    public ImageView google_icon;
    public View auth_btn;
    public TextView user_name;
    public TextView user_email;
    boolean sign_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // Получаем тулбар с меню и прячем его
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_vip, R.id.nav_add_sever)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("******")
                .requestEmail()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        View header = navigationView.getHeaderView(0);
        auth_btn = (View) header.findViewById(R.id.sign_in_button);
        auth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        //Проверяем залогинен ли пользователь
        google_icon = (ImageView) header.findViewById(R.id.google_img);
        user_name = (TextView) header.findViewById(R.id.client_name);
        user_email = (TextView) header.findViewById(R.id.client_email);
        sign_in = GoogleAuth.ifLogged(getApplicationContext(), google_icon, auth_btn, user_name, user_email);
        backPressed = "none";



        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        final String token = task.getResult().getToken();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://ragaban.zzz.com.ua/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        final ServerApi serverApi = retrofit.create(ServerApi.class);
                        Call<String> messages = serverApi.sendToken(token);
                        messages.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, final Response<String> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().contains("TRUE")) {
                                        System.out.println("Токен отправлен - "+ token);
                                    } else {
                                        System.out.println("Наш токен уже в базе");
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





                    }
                });

                FirebaseMessaging.getInstance().subscribeToTopic("news")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        System.out.println(msg);
                    }
                });


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_1", "channel_1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Уведомления");
            channel.enableLights(true);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
            NotificationChannel channel2 = new NotificationChannel("channel_2", "channel_2",
                    NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("Уведомления2");
            channel2.enableLights(true);
            channel2.enableVibration(true);
            notificationManager.createNotificationChannel(channel2);
        }

        Intent intent_fcm = getIntent();
        final String link = intent_fcm.getStringExtra("link");
        if(link == null){

        } else startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+link)));
        Log.d(TAG, "ссылка - "+link);



    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Log.w(TAG, "Google sign in ok");
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String clientPhoto = user.getPhotoUrl().toString();
                            google_icon = (ImageView) findViewById(R.id.google_img);
                            user_name = (TextView) findViewById(R.id.client_name);
                            user_email = (TextView) findViewById(R.id.client_email);
                            GoogleAuth.registration(getApplicationContext(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString(), google_icon, auth_btn, user_name, user_email);



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }


    @Override
    public boolean  onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {

        if(backPressed.contains("goHome")){
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(getBaseContext(), "Двойной клик для выхода.", Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        }
    }
    public void GotoViewServer(View view){
        Intent intent = new Intent(this, View_Server.class);
        View v = (View) view.getParent();
        TextView server_id = (TextView) v.findViewById(R.id.id_server);
        TextView server_name = (TextView) v.findViewById(R.id.server_address);
        intent.putExtra("server_id", server_id.getText().toString());
        intent.putExtra("server_name", server_name.getText().toString());
        startActivity(intent);
    }

    public void addServer(View v){
        final ProgressBar progressBar_addserver = (ProgressBar) findViewById(R.id.progressBar_addServer);
        EditText server_address = (EditText) findViewById(R.id.server_address);
        EditText rates = (EditText) findViewById(R.id.rates);
        String ifLogged[] = GoogleAuth.getUser(getApplicationContext());
        if(server_address.getText().toString().length() < 5){
            Toast.makeText(getApplicationContext(), R.string.err1, Toast.LENGTH_LONG).show();
        }
        else if(server_address.getText().toString().length() > 50){
            Toast.makeText(getApplicationContext(), "Адрес сервера не должен привышать 50 символов.", Toast.LENGTH_LONG).show();
        }
        else if(rates.getText().toString().length() < 1){
            Toast.makeText(getApplicationContext(), "Укажите рейты.", Toast.LENGTH_LONG).show();
        }
        else if(rates.getText().toString().length() > 10){
            Toast.makeText(getApplicationContext(), "Рейты не должен прeвышать 10 символов.", Toast.LENGTH_LONG).show();
        }
        else if(ifLogged[0].contains("none")){
            Toast.makeText(getApplicationContext(), "Только для авторизированных пользователей.", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar_addserver.setVisibility(View.VISIBLE);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ragaban.zzz.com.ua/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final ServerApi serverApi = retrofit.create(ServerApi.class);
            Call<String> messages = serverApi.addServer(server_address.getText().toString(), Integer.parseInt(rates.getText().toString()), chronicle, selectedDate, ifLogged[2]);
            messages.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        if(response.body().contains("TRUE")){
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    finish();
                                    startActivity(getIntent());
                                }
                            }, 2000);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_LONG).show();
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
        }
    }





}
