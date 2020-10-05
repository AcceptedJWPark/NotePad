package com.accepted.notepad.tutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.accepted.notepad.LongPressChecker;
import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.addmemo.Addmemo_MainActivity;
import com.accepted.notepad.main.MainActivity;
import com.accepted.notepad.papermemo.Papermemo_MainActivity;

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
        View view = inflater.inflate(R.layout.tutorial_page3, container, false);

        iv_addmemo = (ImageView)view.findViewById(R.id.iv_addmemo_tutorial);

        setClickTypeEvent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setClickTypeEvent() {

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
                clickType = SaveSharedPreference.getPrefClickType(mContext);
                if (clickType.equals("1")) {
                    Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
                    mainActivity.nextPage(2);
                } else {
//                    Intent intent = new Intent(mContext, Papermemo_MainActivity.class);
//                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("press", "Add btn double");
                clickType = SaveSharedPreference.getPrefClickType(mContext);
                if (clickType.equals("2")) {
                    Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
                    mainActivity.nextPage(2);
                } else {
//                    Intent intent = new Intent(mContext, Papermemo_MainActivity.class);
//                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        };




        iv_addmemo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clickType = SaveSharedPreference.getPrefClickType(mContext);
                if (clickType.equals("3") || clickType.equals("4")) {
                    // 롱클릭 리스너
                    int pressTimeOut = 1000;
                    if (clickType.equals("3")) {
                        pressTimeOut = 1000;
                    } else if (clickType.equals("4")) {
                        pressTimeOut = 3000;
                    }
                    
                    LongPressChecker mLongPressChecker = new LongPressChecker(mContext, pressTimeOut);
                    mLongPressChecker.setOnLongPressListener( new LongPressChecker.OnLongPressListener() {
                        @Override
                        public void onLongPressed() {
                            Log.d("press", "Add btn longpress");
                            Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
                            mainActivity.nextPage(2);
                        }
                    });
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
            mainActivity.nextPage(2);
        }
    };
}
