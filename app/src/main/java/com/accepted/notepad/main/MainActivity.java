package com.accepted.notepad.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
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

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.addmemo.Addmemo_MainActivity;
import com.accepted.notepad.backgound.Background_MainActivity;
import com.accepted.notepad.join.LostID1_MainActivity;
import com.accepted.notepad.password.Password_MainActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Listitem_Memo> arrayList;
    ListAdapter_Memo listAdapter_memo;

    Context mContext;

    String color1_basic = SaveSharedPreference.getBackColor1_basic();
    String color2_basic = SaveSharedPreference.getBackColor2_basic();
    String color3_basic = SaveSharedPreference.gettxtColor1_basic();
    String color4_basic = SaveSharedPreference.geticonColor1_basic();

    String color1_night = SaveSharedPreference.getBackColor1_night();
    String color2_night = SaveSharedPreference.getBackColor2_night();
    String color3_night = SaveSharedPreference.getTxtColor1_night();
    String color4_night = SaveSharedPreference.getIconColor1_night();

    String choosedColor1;
    String choosedColor2;
    String choosedColor3;
    String choosedColor4;
    int colorMode = 1;

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


        ((TextView)findViewById(R.id.tv_maintitle_home)).setText("Notepad");
        mContext = getApplicationContext();

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


        arrayList.add(new Listitem_Memo("홈트레이닝하기 30분 루틴","최근 작성일 : 2020-03-07 11:32AM","1. 스쿼트 5세트 x 20개, 다리는 골반보다 조금 넓게 벌린 상태에서 상체를 세워주고"));
        arrayList.add(new Listitem_Memo("알리오올리오 맛있게 만드는 법","최근 작성일 : 2020-07-12 17:24PM","스파게티 면 200g을 준비한다. 1인분은 파스타 100원짜리 동전 크기 만큼 사용하는데 양이 적다면 500원짜리 동전을 사용한다"));
        arrayList.add(new Listitem_Memo("부동산 정보 유용한 사이트 모음","최근 작성일 : 2020-07-14 17:24PM","서울특별시 정보광장 → 부동산 종합정보, 건축물 정보, 가격정보(공시지가/개별주택 가격), 내게 맞는 아파트 찾기"));
        arrayList.add(new Listitem_Memo("무료 예능 드라마 영화 볼수 있는 사이트 모음","최근 작성일 : 2020-07-29 17:24PM","PC에서는 접속이 안되고 폰으로 열어야 함."));
        arrayList.add(new Listitem_Memo("이마트 장 보기 리스트","최근 작성일 : 2020-08-12 17:24PM","1. 면도기 & 면도크림 : 면도기 탈부착 가능한거로"));
        arrayList.add(new Listitem_Memo("올해 나의 버킷리스트 100선","최근 작성일 : 2020-08-17 19:07PM","악기와 외국어 중 가장 흥미를 느낄만한 분야를 찾아서 시작한다. 중간에 포기하지 않고 다양한 악기와 외국어를 도전해본다."));

        Intent intent = getIntent();
        colorMode = intent.getIntExtra("ColorMode",1);

        ismenu = intent.getBooleanExtra("ismenu",true);
        ismemo = intent.getBooleanExtra("ismemo",true);
        issearch = intent.getBooleanExtra("issearch",true);
        isdate = intent.getBooleanExtra("isdate",true);


        if(issearch)
        {
            ((LinearLayout)findViewById(R.id.ll_searchContainer)).setVisibility(View.VISIBLE);
        }else
        {
            ((LinearLayout)findViewById(R.id.ll_searchContainer)).setVisibility(View.GONE);
        }

        if(colorMode == 1)
        {
            choosedColor1 = color1_basic;
            choosedColor2 = color2_basic;
            choosedColor3 = color3_basic;
            choosedColor4 = color4_basic;
        }else if(colorMode ==2)
        {
            choosedColor1 = color1_night;
            choosedColor2 = color2_night;
            choosedColor3 = color3_night;
            choosedColor4 = color4_night;
        }
        listAdapter_memo = new ListAdapter_Memo (mContext,arrayList,choosedColor1,choosedColor2,choosedColor3,ismemo,isdate);
        footer = getLayoutInflater().inflate(R.layout.memolist_footer,null,false);
        listView.addFooterView(footer);
        listView.setAdapter(listAdapter_memo);

        ((ImageView)findViewById(R.id.btn_addmemo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Addmemo_MainActivity.class);
                intent.putExtra("ColorMode",colorMode);
                startActivity(intent);
            }
        });

        ((TextView)findViewById(R.id.tv_right_home)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Background_MainActivity.class);
                startActivity(intent);
            }
        });

        colorChange(choosedColor1,choosedColor2,choosedColor3,choosedColor4);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, Password_MainActivity.class);
                intent.putExtra("ColorMode",colorMode);
                intent.putExtra("isTutorial",true);
                startActivity(intent);
            }
        });


        drawerLayout();
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

}
