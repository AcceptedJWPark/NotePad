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
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.login.Login_MainActivity;

import androidx.appcompat.app.AppCompatActivity;


public class LostPW2_MainActivity extends AppCompatActivity {


    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_lostpw2_activity);

        context = getApplicationContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((EditText)findViewById(R.id.et_pw_lostpw)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_pw2_lostpw)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ((Button)findViewById(R.id.btn_next_lostpw2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((EditText)findViewById(R.id.et_pw_lostpw)).length() == 0)
                {
                    Toast.makeText(context,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(((EditText)findViewById(R.id.et_pw_lostpw)).length() < 6){
                    Toast.makeText(context,"비밀번호는 6자 이상 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                    else if(!((EditText)findViewById(R.id.et_pw_lostpw)).getText().toString().equals(((EditText)findViewById(R.id.et_pw2_lostpw)).getText().toString()))
                {
                    Toast.makeText(context,"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(context,"비밀번호가 변경되었습니다",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, Login_MainActivity.class);
                    startActivity(intent);
                }

            }
        });





    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
