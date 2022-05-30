package com.ragaban.l2m;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import static com.ragaban.l2m.AddServer.backPressed;

public class vip extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String chronicle;
    public static String down = "bottom_right";
    private String mParam1;
    private String mParam2;
    public static String selectedDate = "none";
    public static Date naw_date = new Date();
    public static EditText server_address;
    public static EditText rates;
    public static View root;
    public static SimpleDateFormat output;
    public vip() {
    }

    public static vip newInstance(String param1, String param2) {
        vip fragment = new vip();
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
        root = inflater.inflate(R.layout.fragment_vip, container, false);
        final ImageView top_left = (ImageView) root.findViewById(R.id.image_top_left);
        final ImageView top_right = (ImageView) root.findViewById(R.id.image_top_right);
        final ImageView bottom_left = (ImageView) root.findViewById(R.id.image_bottom_left);
        final ImageView bottom_right = (ImageView) root.findViewById(R.id.image_bottom_right);
        bottom_right.setBackground(getContext().getDrawable(R.drawable.border));
        final TextView img_rotation = (TextView) root.findViewById(R.id.textView6);
        final TextView code = (TextView) root.findViewById(R.id.textView7);
        TextView copy = (TextView) root.findViewById(R.id.textView8);
        TextView download = (TextView) root.findViewById(R.id.textView9);

        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getContext().getDrawable(R.drawable.border));
                top_right.setBackgroundResource(0);
                bottom_left.setBackgroundResource(0);
                bottom_right.setBackgroundResource(0);
                img_rotation.setText(R.string.top_left);
                code.setText(getText(R.string.rek_top_left));
                down = "top_left";
            }
        });
        top_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getContext().getDrawable(R.drawable.border));
                top_left.setBackgroundResource(0);
                bottom_left.setBackgroundResource(0);
                bottom_right.setBackgroundResource(0);
                img_rotation.setText(R.string.top_right);
                code.setText(getText(R.string.rek_top_right));
                down = "top_right";
            }
        });
        bottom_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getContext().getDrawable(R.drawable.border));
                top_right.setBackgroundResource(0);
                top_left.setBackgroundResource(0);
                bottom_right.setBackgroundResource(0);
                img_rotation.setText("НИЖНИЙ ЛЕВЫЙ");
                code.setText(getText(R.string.rek_bottom_left));
                down = "bottom_left";
            }
        });
        bottom_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getContext().getDrawable(R.drawable.border));
                top_right.setBackgroundResource(0);
                bottom_left.setBackgroundResource(0);
                top_left.setBackgroundResource(0);
                img_rotation.setText(R.string.bottom_right);
                code.setText(getText(R.string.rek_bottom_right));
                down = "bottom_right";
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", code.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Код кнопки скопирован.", Toast.LENGTH_LONG).show();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(down.equals("top_left")){
                    Intent browserIntent = new
                            Intent(Intent.ACTION_VIEW, Uri.parse("http://ragaban.zzz.com.ua/Кнопка L2M Верхняя левая.txt"));
                    startActivity(browserIntent);
                }
                if(down.equals("top_right")){
                    Intent browserIntent = new
                            Intent(Intent.ACTION_VIEW, Uri.parse("http://ragaban.zzz.com.ua/Кнопка L2M Верхняя правая.txt"));
                    startActivity(browserIntent);
                }
                if(down.equals("bottom_left")){
                    Intent browserIntent = new
                            Intent(Intent.ACTION_VIEW, Uri.parse("http://ragaban.zzz.com.ua/Кнопка L2M нижняя левая.txt"));
                    startActivity(browserIntent);
                }
                if(down.equals("bottom_right")){
                    Intent browserIntent = new
                            Intent(Intent.ACTION_VIEW, Uri.parse("http://ragaban.zzz.com.ua/Кнопка L2M нижняя правая.txt"));
                    startActivity(browserIntent);
                }
            }
        });

        backPressed = "goHome";
        return root;
    }


}
