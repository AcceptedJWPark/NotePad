package com.accepted.notepad.join;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.accepted.notepad.R;
import com.accepted.notepad.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;


public class Login_MainActivity extends AppCompatActivity {


    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        context = getApplicationContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((EditText)findViewById(R.id.et_id)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_pw)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ((Button)findViewById(R.id.btn_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Join_MainActivity.class);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        ((TextView)findViewById(R.id.tv_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LostID1_MainActivity.class);
                startActivity(intent);
            }
        });


        ((TextView)findViewById(R.id.tv_lostpw)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LostPW1_MainActivity.class);
                startActivity(intent);
            }
        });



    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
