package com.accepted.notepad.tutorial;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;

import androidx.fragment.app.Fragment;

import static android.view.ViewConfiguration.getLongPressTimeout;

public class Tutorial_Page3 extends Fragment {

    Context mContext;
    String clickType;

    ImageView iv_addmemo;

    long then = 0;
    private long btnPressTime = 0;
    private int doubleClickFlag = 0;
    private final long  CLICK_DELAY = 250;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
        * 선택한 클릭 타입 테스트 하는 부분
        * */
        mContext=getActivity();
        clickType = SaveSharedPreference.getPrefClickType(mContext);
        View view = inflater.inflate(R.layout.tutorial_page3, container, false);

        iv_addmemo = (ImageView)view.findViewById(R.id.iv_addmemo_tutorial);

        setClickTypeEvent();

        return view;
    }

    public void setClickTypeEvent() {
        switch (clickType) {
            case "1":
                iv_addmemo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
                        mainActivity.nextPage(2);
                    }
                });
                break;
            case "2":
                iv_addmemo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (System.currentTimeMillis() > btnPressTime + 1000) {
                            btnPressTime = System.currentTimeMillis();
                            return;
                        }
                        if (System.currentTimeMillis() <= btnPressTime + 1000) {
                            Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
                            mainActivity.nextPage(2);
                        }
                    }
                });
                break;
            case "3":
                iv_addmemo.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                handler.sendEmptyMessageAtTime(3, event.getDownTime() + 1000);
                                break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_MOVE:
                                handler.removeMessages(3);
                                break;
                        }
                        return false;
                    }
                });
                break;
            case "4":
                iv_addmemo.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                handler.sendEmptyMessageAtTime(4, event.getDownTime() + 3000);
                                break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_MOVE:
                                handler.removeMessages(4);
                                break;
                        }
                        return false;
                    }
                });
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
            mainActivity.nextPage(2);
        }
    };
}
