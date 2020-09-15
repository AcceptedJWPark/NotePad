package com.accepted.notepad;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;

public class TabListView extends ListView {
    private Context mContext;

    // 더블텝을 사용하기 위한 변수들
    private boolean mFirstTabEvent;
    private Message mTapMessage;
    private final static int DOUBLE_TAP = 2;
    private final static int SINGLE_TAP = 1;
    private AdapterView<?> mParent;
    private View mView;
    private int mPosition;
    private long mId;
    private final static int DOUBLE_TAP_DELAY = ViewConfiguration.getDoubleTapTimeout();
    private int mPositionHolder = -1;

    private OnItemTapLister mItemTapListener;

    public TabListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initConstructor(context);
    }

    public TabListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConstructor(context);
    }

    public TabListView(Context context) {
        super(context);
        initConstructor(context);
    }

    private void initConstructor(Context context) {
        this.mContext = context;
        super.setOnItemClickListener(mItemClickListener);
    }

    /**
     * 리스트뷰 더블텝 이벤트
     * */
    public void setOnItemTapListener(OnItemTapLister l) {
        this.mItemTapListener = l;
    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mParent = parent;
            mView = view;
            mPosition = position;
            mId = id;

            if (!mFirstTabEvent) {
                mPositionHolder = position;
                mFirstTabEvent = true;
                mTapMessage = mTapMessage == null ? new Message() : mHandler
                        .obtainMessage();
                mHandler.removeMessages(SINGLE_TAP);
                mTapMessage.what = SINGLE_TAP;
                mHandler.sendMessageDelayed(mTapMessage, DOUBLE_TAP_DELAY);
            } else {
                if (mPositionHolder == position) {
                    mHandler.removeMessages(SINGLE_TAP);
                    mPosition = position;
                    mTapMessage = mHandler.obtainMessage();
                    mTapMessage.what = DOUBLE_TAP;
                    mHandler.sendMessageAtFrontOfQueue(mTapMessage);
                    mFirstTabEvent = false;
                } else {
                    mTapMessage = mHandler.obtainMessage();
                    mHandler.removeMessages(SINGLE_TAP);
                    mFirstTabEvent = true;
                    mTapMessage.what = SINGLE_TAP;
                    mPositionHolder = position;
                    mHandler.sendMessageDelayed(mTapMessage, DOUBLE_TAP_DELAY);
                }
            }
        }

    };

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SINGLE_TAP:
                    mFirstTabEvent = false;//이 코드르 삽입함으로서 해당 버그 해결
                    mItemTapListener.OnSingleTap(mParent, mView, mPosition, mId);
                    break;
                case DOUBLE_TAP:
                    mItemTapListener.OnDoubleTap(mParent, mView, mPosition, mId);
                    break;
            }
        }

    };
    //Tab 이벤트 인터페이스
    public interface OnItemTapLister {
        public void OnDoubleTap(AdapterView<?> parent, View view, int position,long id);
        public void OnSingleTap(AdapterView<?> parent, View view, int position,long id);
    }

}