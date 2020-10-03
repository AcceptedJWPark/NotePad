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
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.VolleySingleton;
import com.accepted.notepad.login.Login_MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class LostID2_MainActivity extends AppCompatActivity {


    Context context;
    String phoneNum;
    TextView tv_memID, tv_memID_end, tv_memID_begin, tv_regDate_lostID2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_lostid2_activity);

        context = getApplicationContext();
        phoneNum = getIntent().getStringExtra("Phone");
        tv_memID = findViewById(R.id.tv_memID);
        tv_memID_begin = findViewById(R.id.tv_memID_begin);
        tv_memID_end = findViewById(R.id.tv_memID_end);
        tv_regDate_lostID2 = findViewById(R.id.tv_regDate_lostID2);
        getMemID();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ((Button)findViewById(R.id.btn_login_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Login_MainActivity.class);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.btn_join_lostid)).setOnClickListener(new View.OnClickListener() {
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

    public void getMemID() {

        RequestQueue postRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/getMemID.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("MemID") && !obj.getString("MemID").isEmpty()) {
                        tv_memID.setText(obj.getString("MemID"));
                        long dateTimestamp = obj.getLong("RegDate");
                        Date regDate = new Date(dateTimestamp);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String regDateStr = sdf.format(regDate);

                        tv_regDate_lostID2.setText("회원가입: " + regDateStr);
                    } else {
                        tv_memID_begin.setVisibility(View.GONE);
                        tv_memID_end.setVisibility(View.GONE);
                        tv_regDate_lostID2.setVisibility(View.GONE);
                        tv_memID.setText("아이디가 존재하지 않습니다.");
                    }
                } catch (JSONException e) {
                    tv_memID_begin.setVisibility(View.GONE);
                    tv_memID_end.setVisibility(View.GONE);
                    tv_memID.setText("아이디가 존재하지 않습니다.");
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(context)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Phone", phoneNum);
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }

}
