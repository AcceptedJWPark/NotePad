package com.accepted.notepad.addmemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.papermemo.Papermemo_MainActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Addmemo_MainActivity extends AppCompatActivity {

    LinearLayout ll_low;
    LinearLayout ll_mid;
    LinearLayout ll_high;
    TextView tv_low;
    TextView tv_mid;
    TextView tv_high;
    ImageView iv_low;
    ImageView iv_mid;
    ImageView iv_high;
    ArrayList<String> arrayList;
    Rcv_Adapter rcv_adapter;
    RecyclerView recyclerView;

    EditText fakeTitle;
    EditText fakeContent;
    ArrayList<String> fakeTitleList;
    ArrayList<String> fakeContentList;

    Context context;

    int securityType = 1;
    int clickType = 1;

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

    GradientDrawable shape1;
    GradientDrawable shape2;
    GradientDrawable shape3;
    GradientDrawable shape4;
    GradientDrawable shape5;
    GradientDrawable shape6;

    int colorMode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmemo_activity);

        context = getApplicationContext();


        initKeyBoardListener();


        Intent intent = getIntent();
        colorMode = intent.getIntExtra("ColorMode", 1);

        if (colorMode == 1) {
            choosedColor1 = color1_basic;
            choosedColor2 = color2_basic;
            choosedColor3 = color3_basic;
            choosedColor4 = color4_basic;
        } else if (colorMode == 2) {
            choosedColor1 = color1_night;
            choosedColor2 = color2_night;
            choosedColor3 = color3_night;
            choosedColor4 = color4_night;
        }

        ll_low = findViewById(R.id.ll_low);
        ll_mid = findViewById(R.id.ll_mid);
        ll_high = findViewById(R.id.ll_high);
        tv_low = findViewById(R.id.tv_low);
        tv_mid = findViewById(R.id.tv_mid);
        tv_high = findViewById(R.id.tv_high);
        iv_low = findViewById(R.id.iv_low);
        iv_mid = findViewById(R.id.iv_mid);
        iv_high = findViewById(R.id.iv_high);


        fakeTitle = findViewById(R.id.et_faketitle);
        fakeContent = findViewById(R.id.et_fakecontents);

        fakeTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        fakeContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        colorChange(choosedColor1, choosedColor2, choosedColor3, choosedColor4);


        ((ImageView) findViewById(R.id.img_open_dl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView = findViewById(R.id.rcv);
        arrayList = new ArrayList<>();
        fakeTitleList = new ArrayList<>();
        fakeContentList = new ArrayList<>();

        arrayList.add("직접입력");
        arrayList.add("Fake 1");
        arrayList.add("Fake 2");
        arrayList.add("Fake 3");
        arrayList.add("Fake 4");
        arrayList.add("Fake 5");
        fakeTitleList.add("");
        fakeTitleList.add("Fake 1 제목");
        fakeTitleList.add("Fake 2 제목");
        fakeTitleList.add("Fake 3 제목");
        fakeTitleList.add("Fake 4 제목");
        fakeTitleList.add("Fake 5 제목");
        fakeContentList.add("");
        fakeContentList.add("Fake 1 내용");
        fakeContentList.add("Fake 2 내용");
        fakeContentList.add("Fake 3 내용");
        fakeContentList.add("Fake 4 내용");
        fakeContentList.add("Fake 5 내용");

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcv_adapter = new Rcv_Adapter(arrayList, fakeTitle, fakeContent, fakeTitleList, fakeContentList, shape3, shape4, choosedColor4);
        recyclerView.setAdapter(rcv_adapter);

        clickFakeType();
        clickClickType();

        ((Button) findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (securityType != 1) {
                    if (fakeTitle.length() == 0) {
                        Toast.makeText(context, "Fake 제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (fakeContent.length() == 0) {
                        Toast.makeText(context, "Fake 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(context, Papermemo_MainActivity.class);
                        intent.putExtra("ColorMode", colorMode);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent(context, Papermemo_MainActivity.class);
                    intent.putExtra("ColorMode", colorMode);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    public void clickClickType() {
        ((TextView) findViewById(R.id.btn_normalclick)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.btn_normalclick)).setBackground(shape1);
                ((TextView) findViewById(R.id.btn_doubleclick)).setBackground(shape2);
                ((TextView) findViewById(R.id.btn_longclick)).setBackground(shape2);

                ((TextView) findViewById(R.id.btn_normalclick)).setTextColor(Color.parseColor(choosedColor4));
                ((TextView) findViewById(R.id.btn_doubleclick)).setTextColor(Color.parseColor("#949494"));
                ((TextView) findViewById(R.id.btn_longclick)).setTextColor(Color.parseColor("#949494"));

                ((TextView) findViewById(R.id.btn_normalclick)).setTypeface(Typeface.DEFAULT_BOLD);
                ((TextView) findViewById(R.id.btn_doubleclick)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                ((TextView) findViewById(R.id.btn_longclick)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                clickType = 1;
            }
        });
        ((TextView) findViewById(R.id.btn_doubleclick)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((TextView) findViewById(R.id.btn_normalclick)).setBackground(shape2);
                ((TextView) findViewById(R.id.btn_doubleclick)).setBackground(shape1);
                ((TextView) findViewById(R.id.btn_longclick)).setBackground(shape2);


                ((TextView) findViewById(R.id.btn_doubleclick)).setTypeface(Typeface.DEFAULT_BOLD);
                ((TextView) findViewById(R.id.btn_normalclick)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                ((TextView) findViewById(R.id.btn_longclick)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                ((TextView) findViewById(R.id.btn_doubleclick)).setTextColor(Color.parseColor(choosedColor4));
                ((TextView) findViewById(R.id.btn_normalclick)).setTextColor(Color.parseColor("#949494"));
                ((TextView) findViewById(R.id.btn_longclick)).setTextColor(Color.parseColor("#949494"));

                clickType = 2;
            }
        });
        ((TextView) findViewById(R.id.btn_longclick)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((TextView) findViewById(R.id.btn_normalclick)).setBackground(shape2);
                ((TextView) findViewById(R.id.btn_doubleclick)).setBackground(shape2);
                ((TextView) findViewById(R.id.btn_longclick)).setBackground(shape1);

                ((TextView) findViewById(R.id.btn_normalclick)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                ((TextView) findViewById(R.id.btn_doubleclick)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                ((TextView) findViewById(R.id.btn_longclick)).setTypeface(Typeface.DEFAULT_BOLD);

                ((TextView) findViewById(R.id.btn_longclick)).setTextColor(Color.parseColor(choosedColor4));
                ((TextView) findViewById(R.id.btn_doubleclick)).setTextColor(Color.parseColor("#949494"));
                ((TextView) findViewById(R.id.btn_normalclick)).setTextColor(Color.parseColor("#949494"));

                clickType = 3;
            }
        });


    }

    public void clickFakeType() {
        ll_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_low.setImageResource(R.drawable.low2);
                iv_mid.setImageResource(R.drawable.mid1);
                iv_high.setImageResource(R.drawable.high1);

                tv_low.setTextColor(Color.parseColor("#039C17"));
                tv_mid.setTextColor(Color.parseColor("#949494"));
                tv_high.setTextColor(Color.parseColor("#949494"));

                tv_low.setTypeface(Typeface.DEFAULT_BOLD);
                tv_mid.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_high.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                ((TextView) findViewById(R.id.tv_low1)).setVisibility(View.VISIBLE);

                ((TextView) findViewById(R.id.tv_clicktype)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.tv_clicktype)).setText("보안설정 없음");

                ((LinearLayout) findViewById(R.id.ll_clicktype)).setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);

                ((TextView) findViewById(R.id.tv_fake)).setVisibility(View.GONE);
                fakeTitle.setVisibility(View.GONE);
                fakeContent.setVisibility(View.GONE);

                fakeTitle.clearFocus();
                fakeContent.clearFocus();

                securityType = 1;

            }
        });

        ll_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_low.setImageResource(R.drawable.low1);
                iv_mid.setImageResource(R.drawable.mid2);
                iv_high.setImageResource(R.drawable.high1);

                tv_low.setTextColor(Color.parseColor("#949494"));
                tv_mid.setTextColor(Color.parseColor("#FF9100"));
                tv_high.setTextColor(Color.parseColor("#949494"));

                tv_mid.setTypeface(Typeface.DEFAULT_BOLD);
                tv_low.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_high.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));


                ((TextView) findViewById(R.id.tv_low1)).setVisibility(View.GONE);

                ((TextView) findViewById(R.id.tv_clicktype)).setVisibility(View.GONE);

                ((LinearLayout) findViewById(R.id.ll_clicktype)).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                ((TextView) findViewById(R.id.tv_fake)).setVisibility(View.VISIBLE);
                fakeTitle.setVisibility(View.VISIBLE);
                fakeContent.setVisibility(View.VISIBLE);

                ((TextView) findViewById(R.id.tv_fake)).setText("Fake 글 작성 (비밀번호 틀렸을 때 노출)");

                fakeTitle.clearFocus();
                fakeContent.clearFocus();

                securityType = 2;
            }
        });

        ll_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_low.setImageResource(R.drawable.low1);
                iv_mid.setImageResource(R.drawable.mid1);
                iv_high.setImageResource(R.drawable.high2);

                tv_low.setTextColor(Color.parseColor("#949494"));
                tv_mid.setTextColor(Color.parseColor("#949494"));
                tv_high.setTextColor(Color.parseColor("#FF0911"));

                tv_high.setTypeface(Typeface.DEFAULT_BOLD);
                tv_low.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_mid.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                ((TextView) findViewById(R.id.tv_low1)).setVisibility(View.GONE);

                ((TextView) findViewById(R.id.tv_clicktype)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.tv_clicktype)).setText("클릭 방식을 선택하세요");

                ((LinearLayout) findViewById(R.id.ll_clicktype)).setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);

                ((TextView) findViewById(R.id.tv_fake)).setVisibility(View.VISIBLE);
                fakeTitle.setVisibility(View.VISIBLE);
                fakeContent.setVisibility(View.VISIBLE);

                ((TextView) findViewById(R.id.tv_fake)).setText("Fake 글 작성 (클릭방식 또는 비밀번호 틀렸을 때 노출)");

                fakeTitle.clearFocus();
                fakeContent.clearFocus();

                securityType = 3;
            }
        });
    }

    private void initKeyBoardListener() {
        final int MIN_KEYBOARD_HEIGHT_PX = 150;
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();
                ViewGroup.LayoutParams lp = fakeContent.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        lp.height = (int) convertDpToPixel(100, context);
                        fakeContent.setLayoutParams(lp);
                        View lastChild = ((ScrollView) findViewById(R.id.scrollView)).getChildAt(((ScrollView) findViewById(R.id.scrollView)).getChildCount() - 1);
                        int bottom = lastChild.getBottom() + ((ScrollView) findViewById(R.id.scrollView)).getPaddingBottom();
                        int sy = ((ScrollView) findViewById(R.id.scrollView)).getScrollY();
                        int sh = ((ScrollView) findViewById(R.id.scrollView)).getHeight();
                        int delta = bottom - (sy + sh);

                        ((ScrollView) findViewById(R.id.scrollView)).smoothScrollBy(0, delta);

                        Log.d("Pasha", "SHOW");

                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        fakeContent.setLayoutParams(lp);
                        Log.d("Pasha", "HIDE");
                    }
                }
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void colorChange(String color1, String color2, String color3, String color4) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color2));
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }

        shape1 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.bgr_click_selected);
        shape2 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.bgr_click_unselected);
        shape3 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.bgr_fake_selected);
        shape4 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.bgr_fake_unselected);
        shape5 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.bgr_mainbtn);
        shape6 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.bgr_edittext);

        shape1.setStroke(3, Color.parseColor(color4));
        shape2.setStroke(3, Color.parseColor("#949494"));

        shape3.setStroke(3, Color.parseColor(color4));
        shape4.setStroke(3, Color.parseColor("#949494"));

        shape5.setColor(Color.parseColor(color4));

        shape6.setCornerRadius(15);
        shape6.setColor(Color.parseColor(color1));

        ((TextView) findViewById(R.id.btn_normalclick)).setBackground(shape1);
        ((TextView) findViewById(R.id.btn_doubleclick)).setBackground(shape2);
        ((TextView) findViewById(R.id.btn_longclick)).setBackground(shape2);

        ((TextView) findViewById(R.id.btn_normalclick)).setTextColor(Color.parseColor(color4));
        ((TextView) findViewById(R.id.btn_doubleclick)).setTextColor(Color.parseColor("#949494"));
        ((TextView) findViewById(R.id.btn_longclick)).setTextColor(Color.parseColor("#949494"));

        ((TextView) findViewById(R.id.btn_normalclick)).setTypeface(Typeface.DEFAULT_BOLD);
        ((TextView) findViewById(R.id.btn_doubleclick)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        ((TextView) findViewById(R.id.btn_longclick)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));


        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(30);
        shape.setColor(Color.parseColor(color1));

        ((TextView) findViewById(R.id.tv_maintitle_home)).setText("보안선택");
        ((TextView) findViewById(R.id.tv_maintitle_home)).setTextColor(Color.parseColor(color3));
        ((ImageView) findViewById(R.id.img_open_dl)).setColorFilter(Color.parseColor(color3));
        ((ImageView) findViewById(R.id.img_open_dl)).setImageResource(R.drawable.icon_pre);


        ((RelativeLayout) findViewById(R.id.rl_toolbar)).setBackgroundColor(Color.parseColor(color2));
        ((LinearLayout) findViewById(R.id.ll_clicktype)).setBackgroundColor(Color.parseColor(color2));


        ((TextView) findViewById(R.id.tv_securityType)).setBackgroundColor(Color.parseColor(color1));
        ((TextView) findViewById(R.id.tv_clicktype)).setBackgroundColor(Color.parseColor(color1));
        ((TextView) findViewById(R.id.tv_fake)).setBackgroundColor(Color.parseColor(color1));

        ((TextView) findViewById(R.id.tv_securityType)).setTextColor(Color.parseColor(color3));
        ((TextView) findViewById(R.id.tv_clicktype)).setTextColor(Color.parseColor(color3));
        ((TextView) findViewById(R.id.tv_fake)).setTextColor(Color.parseColor(color3));


        ((LinearLayout) findViewById(R.id.ll_securityContainer)).setBackgroundColor(Color.parseColor(color2));
        ((RelativeLayout) findViewById(R.id.rl_securityContainer)).setBackgroundColor(Color.parseColor(color2));

        fakeTitle.setBackground(shape6);
        fakeContent.setBackground(shape6);
        fakeTitle.setTextColor(Color.parseColor(color3));
        fakeContent.setTextColor(Color.parseColor(color3));
        fakeTitle.setHintTextColor(Color.parseColor(color3));
        fakeContent.setHintTextColor(Color.parseColor(color3));

        ((Button) findViewById(R.id.btn_next)).setBackground(shape5);
        ((Button) findViewById(R.id.btn_next)).setTextColor(Color.parseColor("#ffffff"));

    }


}
