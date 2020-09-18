package com.accepted.notepad.papermemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.VolleySingleton;
import com.accepted.notepad.main.ListAdapter_Memo;
import com.accepted.notepad.main.Listitem_Memo;
import com.accepted.notepad.main.MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Papermemo_MainActivity extends AppCompatActivity {


    Context mContext;
    String memID;

    String choosedColor1;
    String choosedColor2;
    String choosedColor3;
    String choosedColor4;

    String fTitle;
    String fContent;
    String rTitle;
    String rContent;
    int secureType;
    int clickType;
    int memoCode;

    LinearLayout containerPaper;
    LinearLayout containerTxt;
    TextView tv_comp;
    EditText et_title;
    EditText et_contents;
    Button btn_next_papermemo;
    ImageView iv_pre;

    GradientDrawable shape1;
    int colorMode = 1;

    int isReal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.papermemo_main);

        mContext = getApplicationContext();
        memID = SaveSharedPreference.getPrefUserId(mContext);

        ((ImageView)findViewById(R.id.iv_pre)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        containerPaper = findViewById(R.id.ll_cotainer_paper);
        containerTxt = findViewById(R.id.ll_txtcontainer_paper);
        tv_comp = findViewById(R.id.tv_comp);
        et_title = findViewById(R.id.et_title);
        et_contents = findViewById(R.id.et_contents);
        iv_pre = findViewById(R.id.iv_pre);
        btn_next_papermemo = findViewById(R.id.btn_next_papermemo);

        Intent intent2 = getIntent();

        isReal = intent2.getIntExtra("isReal",1);
        memoCode = intent2.getIntExtra("MemoCode", -1);
        secureType = intent2.getIntExtra("SecureType", 1);
        clickType = intent2.getIntExtra("ClickType", 1);
        Log.d("isReal", "" + isReal);
        if(isReal==2) {
//            Toast.makeText(mContext,"real", Toast.LENGTH_SHORT).show();
            String title = intent2.getStringExtra("RTitle");
            String content = intent2.getStringExtra("RContent");
            fTitle = intent2.getStringExtra("Title");

            fContent = intent2.getStringExtra("Content");
            et_title.setText(title);
            et_contents.setText(content);
        } else if(isReal==3) {
//            Toast.makeText(mContext,"fake", Toast.LENGTH_SHORT).show();
            String title = intent2.getStringExtra("Title");
            String content = intent2.getStringExtra("Content");
            rTitle = intent2.getStringExtra("RTitle");
            rContent = intent2.getStringExtra("RContent");
            et_title.setText(title);
            et_contents.setText(content);
        } else {
//            Toast.makeText(mContext,"new", Toast.LENGTH_SHORT).show();
            fTitle = intent2.getStringExtra("Title");
            fContent = intent2.getStringExtra("Content");
        }

        Intent intent = getIntent();
        colorMode = intent.getIntExtra("ColorMode",1);
        choosedColor1 = SaveSharedPreference.getBackColor1(mContext);
        choosedColor2 = SaveSharedPreference.getBackColor2(mContext);
        choosedColor3 = SaveSharedPreference.gettxtColor1(mContext);
        choosedColor4 = SaveSharedPreference.geticonColor1(mContext);
//        if(colorMode == 1)
//        {
//            choosedColor1 = color1_basic;
//            choosedColor2 = color2_basic;
//            choosedColor3 = color3_basic;
//            choosedColor4 = color4_basic;
//        }else if(colorMode ==2)
//        {
//            choosedColor1 = color1_night;
//            choosedColor2 = color2_night;
//            choosedColor3 = color3_night;
//            choosedColor4 = color4_night;
//        }

        background(choosedColor1,choosedColor2,choosedColor3,choosedColor4);

        btn_next_papermemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMemo();
            }
        });
    }


    public void background(String color1, String color2, String color3, String color4)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color2));
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }
        shape1 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_mainbtn);
        shape1.setColor(Color.parseColor(color4));

        containerPaper.setBackgroundColor(Color.parseColor(color2));
        containerTxt.setBackgroundColor(Color.parseColor(color1));

        tv_comp.setTextColor(Color.parseColor(color4));

        et_title.setTextColor(Color.parseColor(color3));
        et_contents.setTextColor(Color.parseColor(color3));
        et_title.setHintTextColor(Color.parseColor(color3));
        et_contents.setHintTextColor(Color.parseColor(color3));

        iv_pre.setColorFilter(Color.parseColor(color3));

        ((LinearLayout)findViewById(R.id.ll_cotainer_paper)).setBackgroundColor(Color.parseColor(color2));
        ((LinearLayout)findViewById(R.id.ll_txtcontainer_paper)).setBackgroundColor(Color.parseColor(color1));
        ((ImageView)findViewById(R.id.iv_pre)).setColorFilter(Color.parseColor(color3));

    }

    public void insertMemo() {
        if (et_title.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (et_contents.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue postRequestQueue = VolleySingleton.getInstance(mContext).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Memo/insertMemo.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);

                    if (obj.getString("result").equals("success")) {
                        Toast.makeText(mContext, "저장되었습니다.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "저장이 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(mContext)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("MemID", memID);
                params.put("SecureType", String.valueOf(secureType));
                params.put("ClickType", String.valueOf(clickType));
                params.put("RTitle", et_title.getText().toString());
                params.put("RContent", et_contents.getText().toString());
                params.put("FTitle", fTitle);
                params.put("FContent", fContent);
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }
}
