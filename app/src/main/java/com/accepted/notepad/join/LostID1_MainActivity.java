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

import com.accepted.notepad.R;
import com.accepted.notepad.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;


public class LostID1_MainActivity extends AppCompatActivity {


    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_lostid1_activity);

        context = getApplicationContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((EditText)findViewById(R.id.et_phone_lostid)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_check_lostid)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });



        ((Button)findViewById(R.id.btn_next_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,LostID2_MainActivity.class);
                startActivity(intent);
            }
        });



    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
