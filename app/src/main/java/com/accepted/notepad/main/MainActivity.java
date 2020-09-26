package com.accepted.notepad.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.LongPressChecker;
import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.TabListView;
import com.accepted.notepad.VolleySingleton;
import com.accepted.notepad.addmemo.Addmemo_MainActivity;
import com.accepted.notepad.backgound.Background_MainActivity;
import com.accepted.notepad.join.LostID1_MainActivity;
import com.accepted.notepad.papermemo.Papermemo_MainActivity;
import com.accepted.notepad.password.Password_MainActivity;
import com.accepted.notepad.tutorial.Tutorial_MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Listitem_Memo> arrayList;
    ListAdapter_Memo listAdapter_memo;

    Context mContext;
    String memID;
    String writeType;
    private long btnPressTime = 0;

    String choosedColor1;
    String choosedColor2;
    String choosedColor3;
    String choosedColor4;

    int colorMode;

    MainActivity mainActivity;

    DrawerLayout dl;
    View v_drawerlayout;
    View footer;

    boolean ismenu;
    boolean ismemo;
    boolean issearch;
    boolean isdate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mainActivity = this;

        mContext = getApplicationContext();

        memID = SaveSharedPreference.getPrefUserId(mContext);
        writeType = SaveSharedPreference.getPrefClickType(mContext);

        String userAppName = SaveSharedPreference.getAppName(mContext);
        ((TextView)findViewById(R.id.tv_maintitle_home)).setText(userAppName);

        arrayList = new ArrayList<>();
        listView = findViewById(R.id.lv_memo);

        dl = (DrawerLayout)findViewById(R.id.drawer);
        v_drawerlayout = (View)findViewById(R.id.view_drawerlayout);

        ((ImageView)findViewById(R.id.img_open_dl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(v_drawerlayout);
            }
        });

        Intent intent = getIntent();
        colorMode = intent.getIntExtra("ColorMode",1);
        choosedColor1 = SaveSharedPreference.getBackColor1(mContext);
        choosedColor2 = SaveSharedPreference.getBackColor2(mContext);
        choosedColor3 = SaveSharedPreference.gettxtColor1(mContext);
        choosedColor4 = SaveSharedPreference.geticonColor1(mContext);

        ismenu = SaveSharedPreference.getMenubarFlag(mContext).equals("Y") ? true : false;
        ismemo = SaveSharedPreference.getSummaryFlag(mContext).equals("Y") ? true : false;
        issearch = SaveSharedPreference.getSearchFlag(mContext).equals("Y") ? true : false;
        isdate = SaveSharedPreference.getRegDateFlag(mContext).equals("Y") ? true : false;

        if(issearch)
        {
            ((LinearLayout)findViewById(R.id.ll_searchContainer)).setVisibility(View.VISIBLE);
        }else
        {
            ((LinearLayout)findViewById(R.id.ll_searchContainer)).setVisibility(View.GONE);
        }

        choosedColor1 = SaveSharedPreference.getBackColor1(mContext);
        choosedColor2 = SaveSharedPreference.getBackColor2(mContext);
        choosedColor3 = SaveSharedPreference.gettxtColor1(mContext);
        choosedColor4 = SaveSharedPreference.geticonColor1(mContext);

        listAdapter_memo = new ListAdapter_Memo (mContext,arrayList,choosedColor1,choosedColor2,choosedColor3,choosedColor4,ismemo,isdate);
        footer = getLayoutInflater().inflate(R.layout.memolist_footer,null,false);
        listView.addFooterView(footer);
        listView.setAdapter(listAdapter_memo);

        ((TextView)findViewById(R.id.tv_right_home)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Background_MainActivity.class);
                startActivity(intent);
            }
        });
        setClickTypeEvent();
//        getBasicMemoList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setClickTypeEvent();
        getBasicMemoList();
    }

    public void drawerLayout()
    {
        ((LinearLayout)findViewById(R.id.ll_drawer_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ((LinearLayout)findViewById(R.id.ll_drawer_pw)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LostID1_MainActivity.class);
                intent.putExtra("ColorMode",colorMode);
                intent.putExtra("isLostLock",true);
                startActivity(intent);
            }
        });

        ((LinearLayout)findViewById(R.id.ll_drawer_click)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ClickChange_MainActivity.class);
                intent.putExtra("ColorMode",colorMode);
                startActivity(intent);
            }
        });

        ((LinearLayout)findViewById(R.id.ll_drawer_bgr)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ((LinearLayout)findViewById(R.id.ll_drawer_manual)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void colorChange(String color1, String color2, String color3, String color4)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color2));
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 30 );
        shape.setColor(Color.parseColor(color1));

        ((RelativeLayout)findViewById(R.id.rl_toolbar)).setBackgroundColor(Color.parseColor(color2));
        ((TextView)findViewById(R.id.tv_maintitle_home)).setTextColor(Color.parseColor(color3));
        ((LinearLayout)findViewById(R.id.ll_searchContainer)).setBackgroundColor(Color.parseColor(color2));
        ((LinearLayout)findViewById(R.id.ll_searchContainer2)).setBackgroundColor(Color.parseColor(color2));

        ((RelativeLayout)findViewById(R.id.rl_searchContainer)).setBackground(shape);

        ((ImageView)findViewById(R.id.iv_search)).setColorFilter(Color.parseColor(color3));
        ((EditText)findViewById(R.id.et_search)).setHintTextColor(Color.parseColor(color3));
        ((EditText)findViewById(R.id.et_search)).setTextColor(Color.parseColor(color3));

        ((LinearLayout)findViewById(R.id.container)).setBackgroundColor(Color.parseColor(color1));
        ((RelativeLayout)findViewById(R.id.rl_container)).setBackgroundColor(Color.parseColor(color1));

        ((ImageView)findViewById(R.id.btn_addmemo)).setColorFilter(Color.parseColor(color4));

        ((TextView)findViewById(R.id.tv_right_home)).setText("배경설정");
        ((TextView)findViewById(R.id.tv_right_home)).setTextColor(Color.parseColor(color4));
        ((TextView)findViewById(R.id.tv_right_home)).setVisibility(View.VISIBLE);

        ((LinearLayout)findViewById(R.id.ll_drawer)).setBackgroundColor(Color.parseColor(color2));

        ((TextView)findViewById(R.id.tv_drawer_login)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_drawer_bgr)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_drawer_pw)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_drawer_click)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_drawer_manual)).setTextColor(Color.parseColor(color3));

        ((ImageView)findViewById(R.id.iv_drawer_login)).setColorFilter(Color.parseColor(color3));
        ((ImageView)findViewById(R.id.iv_drawer_bgr)).setColorFilter(Color.parseColor(color3));
        ((ImageView)findViewById(R.id.iv_drawer_pw)).setColorFilter(Color.parseColor(color3));
        ((ImageView)findViewById(R.id.iv_drawer_click)).setColorFilter(Color.parseColor(color3));
        ((ImageView)findViewById(R.id.iv_drawer_manual)).setColorFilter(Color.parseColor(color3));

        footer.setBackgroundColor(Color.parseColor(color1));

        if(ismenu)
        {
            ((ImageView)findViewById(R.id.img_open_dl)).setColorFilter(Color.parseColor(color3));
        }else
        {
            ((ImageView)findViewById(R.id.img_open_dl)).setColorFilter(Color.parseColor(color2));
        }
    }

    public void getBasicMemoList() {
        arrayList = new ArrayList<>();
        RequestQueue postRequestQueue = VolleySingleton.getInstance(mContext).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Memo/getBasicMemoList.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray objArray = new JSONArray(response);
                    for (int i = 0; i < objArray.length(); i++) {
                        JSONObject obj = objArray.getJSONObject(i);
                        Listitem_Memo memo;

                        long dateTimestamp = obj.getLong("RegDate");
                        Date regDate = new Date(dateTimestamp);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String regDateStr = sdf.format(regDate);

                        int memoCode = obj.getInt("MemoCode");
                        String secureType = obj.getString("SecureType");


                        memo = new Listitem_Memo(memoCode, obj.getString("RTitle"), obj.getString("RContent"), obj.getString("FTitle"), obj.getString("FContent"), regDateStr, secureType, obj.getString("ClickType"));
                        arrayList.add(memo);
                    }

                    listAdapter_memo = new ListAdapter_Memo (mContext,arrayList,choosedColor1,choosedColor2,choosedColor3,choosedColor4,ismemo,isdate);
                    footer = getLayoutInflater().inflate(R.layout.memolist_footer,null,false);
                    listView.addFooterView(footer);
                    listView.setAdapter(listAdapter_memo);
                    colorChange(choosedColor1,choosedColor2,choosedColor3,choosedColor4);
                    drawerLayout();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(mContext)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("MemID", memID);
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }

    public void setClickTypeEvent() {
        String writeType = SaveSharedPreference.getPrefClickType(mContext);
        ImageView btn_addmemo = ((ImageView) findViewById(R.id.btn_addmemo));

        GestureDetector gd; GestureDetector.OnDoubleTapListener listener;
        gd = new GestureDetector(mContext, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                Log.d("press", "down");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d("press", "press");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d("press", "up");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }

        });

        listener = new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d("press", "Add btn single");
                if (writeType.equals("1")) {
                    Intent intent = new Intent(mContext, Addmemo_MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, Papermemo_MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("press", "Add btn double");
                if (writeType.equals("2")) {
                    Intent intent = new Intent(mContext, Addmemo_MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, Papermemo_MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        };

        // 롱클릭 리스너
        int pressTimeOut = 1000;
        if (writeType.equals("3") || writeType.equals("4")) {
            if (writeType.equals("3")) {
                pressTimeOut = 1000;
            } else if (writeType.equals("4")) {
                pressTimeOut = 3000;
            } else {
                Intent intent = new Intent(mContext, Papermemo_MainActivity.class);
                startActivity(intent);
            }
        }
        LongPressChecker mLongPressChecker = new LongPressChecker(mContext, pressTimeOut);
        mLongPressChecker.setOnLongPressListener( new LongPressChecker.OnLongPressListener() {
            @Override
            public void onLongPressed() {
                Log.d("press", "Add btn longpress");
                Intent intent = new Intent(mContext, Addmemo_MainActivity.class);
                startActivity(intent);
            }
        });

        btn_addmemo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (writeType.equals("3") || writeType.equals("4")) {
                    mLongPressChecker.deliverMotionEvent( v, event );
                }

                if(gd != null) {
                    gd.onTouchEvent(event);
                    return true;
                }

                return false;
            }
        });

        gd.setOnDoubleTapListener(listener);
    }
}
